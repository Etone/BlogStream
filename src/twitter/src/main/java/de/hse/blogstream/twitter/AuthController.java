package de.hse.blogstream.twitter;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;

@Controller
public class AuthController {

    @Inject
    public AuthController(Twitter twitter, ConnectionRepository repository){
        DataAccessRepos.setRepos(new DataAccessRepos(twitter, repository));

    }

    @GetMapping("/auth")
    public String auth(){
        if(DataAccessRepos.getRepos().getRepository().findPrimaryConnection(Twitter.class) == null){
            return "redirect:/connect/twitter";
        } else {
            return "twitterConnected";
        }
    }

}
