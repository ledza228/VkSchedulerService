package by.ledza.orderlab.security.jwt;

import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Component
public class JwtTokenProvider {

    @Autowired
    UserService userService;

    public String resolveToken(HttpServletRequest request){
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    public void addToken(HttpServletResponse response, String token){
        response.setHeader(HttpHeaders.AUTHORIZATION, token);
    }

    public OAuth2AuthenticationToken authenticateToken(String token){
        String id;
        try {
            id = JwtUtils.getIdFromJwt(token);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

        UserCreds userCreds = userService.getUser(id);

        if (userCreds == null)
            return null;

        Map<String, Object> attributes = convertUserObjectToMap(userCreds);

        Set<GrantedAuthority> authorities = new LinkedHashSet();
        authorities.add(new OAuth2UserAuthority(attributes));

        String attributeKey = "id";
        DefaultOAuth2User user = new DefaultOAuth2User(authorities, attributes, attributeKey);
        return new OAuth2AuthenticationToken(user, authorities, "vk");
    }

    private Map<String, Object> convertUserObjectToMap(UserCreds user){
        Map<String, Object> attributes = new HashMap<>();

        attributes.put("id", user.getId());
        attributes.put("first_name", user.getFirst_name());
        attributes.put("last_name", user.getLast_name());
        attributes.put("photo_max", user.getPhoto_max());

        return attributes;
    }

}
