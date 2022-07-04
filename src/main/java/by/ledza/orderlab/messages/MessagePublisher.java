package by.ledza.orderlab.messages;

import by.ledza.orderlab.core.OrderSender;
import by.ledza.orderlab.model.Order;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.time.Period;
import java.util.logging.Logger;

@Component
public class MessagePublisher implements OrderSender {

    Logger logger = Logger.getLogger("RabbitMQ_Publisher");

    private static final String DELAY_QUEUE_NAME = "delayedOrderQueue";

    private static final String DELAY_EXCHANGE_NAME = "orderQueueExchange";

    private static final Integer SECONDS_BEFORE_CREATING_THREAD = 20;

    private static final Integer MILLISECONDS_IN_SECOND = 1000;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Override
    public void sendOrder(Order order){
        OffsetDateTime time = order.getDateTime();
        String id = order.getId().toString();

        long between = time.toInstant().toEpochMilli() - OffsetDateTime.now().toInstant().toEpochMilli();
        long delayTime =  between - (long) MILLISECONDS_IN_SECOND * SECONDS_BEFORE_CREATING_THREAD;

        rabbitTemplate.send(
                DELAY_EXCHANGE_NAME,
                DELAY_QUEUE_NAME,
                MessageBuilder
                        .withBody(id.getBytes())
                        .setHeader("x-delay", delayTime)
                        .build()
        );
        logger.info("Sent to RabbitMQ order " + id);

    }

}
