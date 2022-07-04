package by.ledza.orderlab.service;

import by.ledza.orderlab.model.Order;

import java.util.List;

public interface OrderService {

    void createOrder(String userId, Order order);

    List<Order> getAllUserOrders(String userId);

    Order getOrderById(Integer id);

    void deleteUserOrder(String userId, Integer orderId);

    List<Order> getFarOrders();

    boolean isOrderFar(Order order);

    void changeToNotActive(Order order);
}
