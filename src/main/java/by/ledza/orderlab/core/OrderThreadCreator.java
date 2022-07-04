package by.ledza.orderlab.core;

import by.ledza.orderlab.model.Order;
import by.ledza.orderlab.service.VkService;
import by.ledza.orderlab.vk.VkUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class OrderThreadCreator {

    Logger logger = Logger.getLogger("ThreadCreator");
    @Autowired
    VkService vkService;

    public void createThread(Order order) {
        VkUser vkUser = vkService.getVkUser(order.getOwner());

        OrderThread orderRunnableThread = new OrderThread(vkUser, order);
        Thread thread = new Thread(orderRunnableThread);
        thread.start();
        logger.info("Thread was created for " + order.getId() + " order" );
    }

}
