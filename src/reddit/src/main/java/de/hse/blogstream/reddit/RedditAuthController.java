package de.hse.blogstream.reddit;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Controller
public class RedditAuthController{

    @Autowired
    private OAuth2RestTemplate redditRestTemplate;


    @RequestMapping("/reddit/authenticate")
    public String getAuthToken() {
        JsonNode node = redditRestTemplate.getForObject(
                "https://oauth.reddit.com/api/v1/me", JsonNode.class);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(node.get("name").asText(),
                        redditRestTemplate.getAccessToken().getValue(),
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:user";
    }

}
