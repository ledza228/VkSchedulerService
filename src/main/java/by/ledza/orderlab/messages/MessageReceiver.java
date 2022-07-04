package by.ledza.orderlab.messages;


import by.ledza.orderlab.core.OrderThreadCreator;
import by.ledza.orderlab.service.MessageReceiverService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import javax.persistence.Access;
import java.util.logging.Logger;

@Component
public class MessageReceiver {


    Logger logger = Logger.getLogger("RabbitMQ_Receiver");
    @Autowired
    MessageReceiverService messageReceiverService;

    private static final String DELAY_QUEUE_NAME = "delayedOrderQueue";

    private static final String DELAY_EXCHANGE_NAME = "orderQueueExchange";


    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = DELAY_QUEUE_NAME),
    exchange = @Exchange(value = DELAY_EXCHANGE_NAME, delayed = "true"), key = DELAY_QUEUE_NAME))
    public void listen(Message<String> in){
        Integer id = Integer.parseInt(in.getPayload());
        logger.info("Gotten from RabbitMQ " + id);

        messageReceiverService.doOrder(id);

    }

}
