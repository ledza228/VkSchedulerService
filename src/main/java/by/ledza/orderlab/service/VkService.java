package by.ledza.orderlab.service;

import by.ledza.orderlab.dto.ConversationDTO;
import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.vk.VkUser;

import java.util.List;

public interface VkService {

    VkUser getVkUser(UserCreds user);

    List<ConversationDTO> getConversations(String userId, Integer length, Integer offset);

    String getConversationNameById(UserCreds user, Integer id);
}
