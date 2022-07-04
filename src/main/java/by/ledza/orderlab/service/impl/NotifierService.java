package by.ledza.orderlab.service.impl;

import by.ledza.orderlab.notifier.Distributor;
import by.ledza.orderlab.notifier.Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class NotifierService {

    @Autowired
    List<Subscriber> subscribers;

    @Autowired
    Distributor distributor;

    @PostConstruct
    void subscribeAll(){
        subscribers.forEach(subscriber -> distributor.subscribe(subscriber));
    }

}
