package by.ledza.orderlab.controller;

import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.service.VkService;
import by.ledza.orderlab.service.impl.UserServiceImpl;
import by.ledza.orderlab.vk.VkUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.api.sdk.objects.messages.ConversationWithMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vk/")
public class VkController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private VkService vkService;

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Object getPrincipal(@AuthenticationPrincipal OAuth2User principal){
        return principal.getAttributes();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<UserCreds> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/conversations")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ConversationWithMessage> getVkConversations(@AuthenticationPrincipal OAuth2User principal,
                                                            @RequestParam(defaultValue="10") Integer length){

        return vkService.getConversations(principal.getName(), length);
    }

}
