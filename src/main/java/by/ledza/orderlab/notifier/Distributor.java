package by.ledza.orderlab.notifier;

import by.ledza.orderlab.model.Order;

public interface Distributor {

    void subscribe(Subscriber subscriber);

    void notifyAllAbout(Order order);
}
