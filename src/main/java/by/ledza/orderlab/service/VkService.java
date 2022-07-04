package by.ledza.orderlab.service;

import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.vk.VkUser;
import com.vk.api.sdk.objects.messages.ConversationWithMessage;

import java.util.List;

public interface VkService {

    VkUser getVkUser(UserCreds user);

    List<ConversationWithMessage> getConversations(String userId, Integer length);
}
