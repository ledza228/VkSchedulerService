package by.ledza.orderlab.security.oauth2;

import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.security.jwt.JwtUtils;
import by.ledza.orderlab.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User user = (OAuth2User) authentication.getPrincipal();
        createNewUser(user);

        String jwt = JwtUtils.createJwt(user.getName());
        response.getWriter().write(jwt);

    }

    private void createNewUser(OAuth2User user){
        if (userService.getUser(user.getName()) == null){
            UserCreds newUser = objectMapper.convertValue(user.getAttributes(), UserCreds.class);
            userService.save(newUser);
        }
    }

}
