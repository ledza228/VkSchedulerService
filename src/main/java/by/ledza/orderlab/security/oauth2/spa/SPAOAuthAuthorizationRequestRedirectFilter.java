package by.ledza.orderlab.security.oauth2.spa;

import lombok.SneakyThrows;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class SPAOAuthAuthorizationRequestRedirectFilter extends OAuth2AuthorizationRequestRedirectFilter {


    @SneakyThrows
    public SPAOAuthAuthorizationRequestRedirectFilter(ClientRegistrationRepository clientRegistrationRepository, AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository) {
        super(clientRegistrationRepository);
        super.setAuthorizationRequestRepository(authorizationRequestRepository);

        Field field = OAuth2AuthorizationRequestRedirectFilter.class.getDeclaredField("authorizationRedirectStrategy");
        field.setAccessible(true);

        field.set(this, new SPARedirectStrategy());
    }


}
