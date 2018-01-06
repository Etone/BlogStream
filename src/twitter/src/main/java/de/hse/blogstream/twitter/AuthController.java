package de.hse.blogstream.twitter;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;

@Controller
public class AuthController {

    private final ConnectionRepository repository;
    private final Twitter twitter;

    @Inject
    public AuthController(Twitter twitter, ConnectionRepository repository){
        this.twitter = twitter;
        this.repository = repository;

    }

    @GetMapping("/auth")
    public String auth(){
        if(repository.findPrimaryConnection(Twitter.class) == null){
            return "redirect:/connect/twitter";
        } else {
            return "twitterConnected";
        }
    }

}
