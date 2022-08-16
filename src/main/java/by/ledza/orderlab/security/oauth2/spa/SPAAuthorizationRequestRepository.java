package by.ledza.orderlab.security.oauth2.spa;

import com.zaxxer.hikari.util.ClockSource;
import org.springframework.scheduling.annotation.Scheduled;
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

    private static final int SECONDS_IN_MINUTE = 60;

    private static final int MILLISECONDS_IN_SECOND = 1000;

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

        if (request.getHeader("X-AUTH-TOKEN") == null)
            return null;

        Integer id = Integer.parseInt(request.getHeader("X-AUTH-TOKEN"));

        OAuth2AuthorizationRequest authorizationRequest = authorizationContainer.get(id);
        authorizationContainer.remove(id);
        return authorizationRequest;
    }

    @Scheduled(fixedDelay = MILLISECONDS_IN_SECOND * SECONDS_IN_MINUTE * 20)
    private void clearMap(){
        authorizationContainer.clear();
    }

}
