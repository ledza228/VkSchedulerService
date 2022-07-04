package by.ledza.orderlab.service.impl;

import by.ledza.orderlab.core.OrderThreadCreator;
import by.ledza.orderlab.model.Order;
import by.ledza.orderlab.service.MessageReceiverService;
import by.ledza.orderlab.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageReceiverServiceImpl implements MessageReceiverService {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderThreadCreator orderThreadCreator;

    @Override
    public void doOrder(Integer orderId) {
        Order order = orderService.getOrderById(orderId);

        if (order != null)
            orderThreadCreator.createThread(order);
    }

}
