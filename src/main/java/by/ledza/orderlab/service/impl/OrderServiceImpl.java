package by.ledza.orderlab.service.impl;

import by.ledza.orderlab.exceptions.OrderNotFoundedException;
import by.ledza.orderlab.exceptions.UserNotFoundedException;
import by.ledza.orderlab.model.Order;
import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.notifier.Distributor;
import by.ledza.orderlab.notifier.Subscriber;
import by.ledza.orderlab.repository.OrderRepository;
import by.ledza.orderlab.service.OrderService;
import by.ledza.orderlab.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService, Distributor {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    List<Subscriber> subscribers = new ArrayList<>();

    public void createOrder(String userId, Order order){

        order.setIsActive(true);

        if (order.getDateTime().isBefore(OffsetDateTime.now()))
            throw new RuntimeException("Date can't be in past!");

        UserCreds user = userService.getUser(userId);
        if (user == null)
            throw new UserNotFoundedException();

        order.setOwner(user);
        order = orderRepository.save(order);

        notifyAllAbout(order);
    }

    public List<Order> getAllUserOrders(String userId){

        UserCreds user = userService.getUser(userId);
        if (user == null)
            throw new UserNotFoundedException();

        return user.getOrders();
    }


    public void deleteUserOrder(String userId, Integer orderId){
        UserCreds user = userService.getUser(userId);
        if (user == null)
            throw new UserNotFoundedException();

        List<Order> orders = orderRepository.findAllByOwner(user);

        Order userOrder = orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findAny().orElseThrow(() -> new OrderNotFoundedException(orderId));

        orderRepository.delete(userOrder);
    }


    public List<Order> getFarOrders(){
        OffsetDateTime dateTime = OffsetDateTime.now().plusHours(1);
        return orderRepository.findAllByIsActiveAndDateTimeBefore(true, dateTime).stream()
                .filter(this::isOrderFar)
                .collect(Collectors.toList());
    }

    public boolean isOrderFar(Order order){
        return order.getDateTime().isBefore(OffsetDateTime.now().plusHours(1));
    }

    public Order getOrderById(Integer id){
        return orderRepository.findById(id).orElse(null);
    }

    public void changeToNotActive(Order order){
        order.setIsActive(false);
        orderRepository.save(order);
    }


    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void notifyAllAbout(Order order) {
        subscribers.forEach(subscriber -> subscriber.notifiedAbout(order));
    }
}
