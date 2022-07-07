package by.ledza.orderlab.security.oauth2.spa;

import by.ledza.orderlab.security.oauth2.spa.dto.Oauth2SPAResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.web.RedirectStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SPARedirectStrategy implements RedirectStrategy {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {

        Oauth2SPAResponse oauth2SPAResponse = new Oauth2SPAResponse();
        oauth2SPAResponse.setUrlRedirect(url);
        oauth2SPAResponse.setSession(request.getSession().getId());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.getWriter().write(objectMapper.writeValueAsString(oauth2SPAResponse));
    }
}
