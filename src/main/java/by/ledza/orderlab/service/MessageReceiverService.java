package by.ledza.orderlab.service;

import by.ledza.orderlab.model.Order;

public interface MessageReceiverService {

    void doOrder(Integer orderId);

}
