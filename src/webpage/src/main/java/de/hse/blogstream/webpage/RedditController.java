package de.hse.blogstream.webpage;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/reddit")
public class RedditController {

    @Lazy
    @Autowired
    private RestTemplate redditTemplate;

    @Autowired
    private EurekaClient eureka;

    private String redditUrl(){
        InstanceInfo instance = eureka.getNextServerFromEureka("blogstream-reddit", false);
        return instance.getHomePageUrl();
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    private boolean isAuthenticated = false;

    @GetMapping("")
    public String twitter(){
        if(isAuthenticated){
            return "redirect:/reddit/subreddits";
        } else {
            return "redirect:/reddit/auth";
        }
    }

    @GetMapping("/auth")
    public String auth(){
        isAuthenticated = true;
        return "redirect:" + redditUrl() + "reddit/auth";
    }

    @GetMapping("/subreddits")
    public String subreddits(Model m){
        List<String> subreddits = redditTemplate.getForObject(redditUrl() + "reddit/subreddits", (Class<List<String>>)(Object)List.class);
        m.addAttribute("subreddits", subreddits);
        String user = redditTemplate.getForObject(redditUrl() + "reddit/user", String.class);
        m.addAttribute("username", user);
        System.out.println(subreddits);
        System.out.println(user);
        return "reddit";
    }

    @GetMapping("/subreddit/{subredditname}")
    public String postsOfSubreddit(@PathVariable String subredditname, Model m)
    {
        List<DisplayPost> posts = redditTemplate.getForObject(redditUrl() + "reddit/subreddit/" + subredditname, (Class<List<DisplayPost>>)(Object)List.class);
        m.addAttribute("posts", posts);
        return "redditposts";
    }
}
