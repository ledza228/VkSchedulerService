package by.ledza.orderlab.controller;

import by.ledza.orderlab.dto.ConversationDTO;
import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.service.VkService;
import by.ledza.orderlab.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    /*development endpoint*/
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserCreds> getAllUsers(){
        return null;
    }

    @GetMapping("/conversations")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ConversationDTO> getVkConversations(@AuthenticationPrincipal OAuth2User principal,
                                                    @RequestParam(defaultValue="10") Integer length,
                                                    @RequestParam(defaultValue="0") Integer offset){

        return vkService.getConversations(principal.getName(), length, offset);
    }

}
