package by.ledza.orderlab.service.impl;

import by.ledza.orderlab.exceptions.VkTokenException;
import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.service.UserService;
import by.ledza.orderlab.service.VkService;
import by.ledza.orderlab.vk.VkUser;
import com.vk.api.sdk.objects.messages.ConversationWithMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkServiceImpl implements VkService {

    @Autowired
    TokenServiceImpl tokenService;

    @Autowired
    UserService userService;

    public VkUser getVkUser(UserCreds user){
        try {
            return new VkUser(tokenService.decryptToken(user.getVk_token().getAuthToken()));
        }
        catch (Exception e){
            throw new VkTokenException("Problems with vk token");
        }
    }

    @Override
    public List<ConversationWithMessage> getConversations(String userId, Integer length) {
        UserCreds user = userService.getUser(userId);
        return getVkUser(user).getConversations(length);
    }


}
