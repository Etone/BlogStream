package de.hse.blogstream.webpage;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

    @Autowired
    private EurekaClient eureka;

    @Autowired
    private RestTemplate template;

    private String twitterURL(){
        InstanceInfo instance = eureka.getNextServerFromEureka("blogstream-twitter", false);
        return instance.getHomePageUrl();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @GetMapping("")
    public String twitter(Model m){
        return "redirect:/twitter/auth";
    }

    @GetMapping("/auth")
    public String auth(){
        return "redirect:" + twitterURL() + "auth";
    }

    @GetMapping("/posts")
    public String posts(Model m){
        return "twitter";
    }
}
