package by.ledza.orderlab.core;

import by.ledza.orderlab.model.Order;
import by.ledza.orderlab.vk.VkUser;
import lombok.SneakyThrows;

import java.time.OffsetDateTime;

public class OrderThread implements Runnable {

    VkUser vkUser;

    Order order;

    public OrderThread(VkUser vkUser, Order order) {
        this.vkUser = vkUser;
        this.order = order;
    }

    @SneakyThrows
    @Override
    public void run() {

        OffsetDateTime now = OffsetDateTime.now();
        while (!(now.equals(order.getDateTime()) || now.isAfter(order.getDateTime()))){
            now = OffsetDateTime.now();
        }

        System.out.println("Started sending " + order.getId() + " " + OffsetDateTime.now());
        vkUser.sendMessage(order.getConversationId(),order.getText());
        System.out.println("Ended sending " + order.getId() + " " + OffsetDateTime.now());
    }
}
