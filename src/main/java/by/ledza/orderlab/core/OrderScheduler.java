package by.ledza.orderlab.core;

import by.ledza.orderlab.model.Order;
import by.ledza.orderlab.notifier.Subscriber;
import by.ledza.orderlab.service.OrderService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;


@Component
public class OrderScheduler implements Subscriber {

    Logger logger = Logger.getLogger("scheduler");

    @Autowired
    OrderService orderService;

    @Autowired
    OrderSender orderSender;

    public void sendFarOrderForPreparation(Order order){
        logger.info("Order " + order.getId() + " was sent to Sender");
        orderSender.sendOrder(order);
    }


    @Scheduled(fixedRate = 30*60*1000)
    public void checkOrderTime(){
        List<Order> orders = orderService.getFarOrders();

        for (Order order: orders){
            sendFarOrderForPreparation(order);
            orderService.changeToNotActive(order);
        }
    }

    @Override
    public void notifiedAbout(Order order) {
        logger.info("Scheduler was notified about new order " + order.getId());
        if (orderService.isOrderFar(order)){
            sendFarOrderForPreparation(order);
            orderService.changeToNotActive(order);
        }
    }

}
