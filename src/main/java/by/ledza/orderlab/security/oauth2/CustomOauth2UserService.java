package by.ledza.orderlab.security.oauth2;

import by.ledza.orderlab.model.UserCreds;
import by.ledza.orderlab.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    @Value("${spring.security.oauth2.client.provider.vk.user-name-attribute}")
    private String attributeKeyResponse;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println(oAuth2User.getAttributes());
        if (!userRequest.getClientRegistration().getRegistrationId().equals("vk"))
            return oAuth2User;

        Map<String, Object> attributes = (Map<String, Object>) ((List) oAuth2User.getAttribute(attributeKeyResponse)).get(0);

        Set<GrantedAuthority> authorities = new LinkedHashSet();
        authorities.add(new OAuth2UserAuthority(attributes));

        String attributeKey = "id";
        OAuth2User user = new DefaultOAuth2User(authorities, attributes, attributeKey);

        return user;
    }


}
