package de.hse.blogstream.webpage;

import com.google.common.reflect.TypeToken;
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

import java.lang.reflect.Type;
import java.util.List;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

    @Autowired
    private EurekaClient eureka;

    @Autowired
    private RestTemplate template;

    private boolean isAuthenticated = false;

    private String twitterURL(){
        InstanceInfo instance = eureka.getNextServerFromEureka("blogstream-twitter", false);
        return instance.getHomePageUrl();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @GetMapping("")
    public String twitter(){
        if(isAuthenticated){
            return "redirect:/twitter/post";
        } else {
            return "redirect:/twitter/auth";
        }
    }

    @GetMapping("/auth")
    public String auth(){
        isAuthenticated = true;
        return "redirect:" + twitterURL() + "auth";
    }

    @GetMapping("/post")
    public String posts(Model m){
        List<DisplayPost> displayable = template.getForObject(twitterURL() + "data/posts", (Class<List<DisplayPost>>)(Object)List.class);
        m.addAttribute("displayable", displayable);
        return "twitter";
    }
}
