package by.ledza.orderlab.notifier;

import by.ledza.orderlab.model.Order;

public interface Subscriber {

    void notifiedAbout(Order order);

}
