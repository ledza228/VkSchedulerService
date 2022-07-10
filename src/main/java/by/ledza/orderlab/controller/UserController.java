package by.ledza.orderlab.controller;

import by.ledza.orderlab.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('ROLE_USER')")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/me")
    public Object getMe(@AuthenticationPrincipal OAuth2User principal){
        return principal.getAttributes();
    }

    @PatchMapping("/edit/token")
    public String addToken(@AuthenticationPrincipal OAuth2User principal, @RequestBody String token){
        userService.editToken(principal.getName(), token);
        return "Success";
    }

    @GetMapping("/token")
    public Boolean isThereToken(@AuthenticationPrincipal OAuth2User principal){
        return userService.getUser(principal.getName()).getVk_token() != null;
    }

}
