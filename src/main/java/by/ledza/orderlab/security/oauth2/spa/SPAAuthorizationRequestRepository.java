package by.ledza.orderlab.security.oauth2.spa;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SPAAuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    ConcurrentHashMap<Integer, OAuth2AuthorizationRequest> authorizationContainer = new ConcurrentHashMap<>();

    Random random = new Random();

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return null;
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        Integer id = random.nextInt();
        authorizationContainer.put(id, authorizationRequest);

        response.setHeader("X-AUTH-TOKEN", id.toString());
        response.setHeader("Access-Control-Expose-Headers", "X-AUTH-TOKEN");
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {

        Integer id = Integer.parseInt(request.getHeader("X-AUTH-TOKEN"));

        OAuth2AuthorizationRequest authorizationRequest = authorizationContainer.get(id);
        authorizationContainer.remove(id);
        return authorizationRequest;
    }
}
