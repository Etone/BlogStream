package de.hse.blogstream.reddit;

import com.fasterxml.jackson.databind.JsonNode;
import de.hse.blogstream.webpage.DisplayPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RedditAuthController{

    @Autowired
    private OAuth2RestTemplate redditRestTemplate;

    @RequestMapping("reddit/auth")
    public String getAuthToken() throws InvalidRequestException {
        JsonNode node = redditRestTemplate.getForObject(
                "https://oauth.reddit.com/api/v1/me", JsonNode.class);
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(node.get("name").asText(),
                        redditRestTemplate.getAccessToken().getValue(),
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        SecurityContextHolder.getContext().setAuthentication(auth);
        return "redirect:/reddit/subreddits";
    }

    @GetMapping("reddit/subreddits")
    public String subreddits(Model m){

        JsonNode userData = redditRestTemplate.getForObject("https://oauth.reddit.com/api/v1/me", JsonNode.class);
        JsonNode name = userData.findValue("display_name_prefixed");
        if(name == null)
        {
            name = userData.findValue("name");
        }
        m.addAttribute("username",  name.toString());
        JsonNode subredditsJson = redditRestTemplate.getForObject("https://oauth.reddit.com/subreddits/mine/subscriber", JsonNode.class);
        List<String> subreddits = subredditsJson.findValuesAsText("display_name_prefixed");
        m.addAttribute("subreddits", subreddits);
        return "subreddits";
    }

    @GetMapping("/subreddit/r/{subreddit}")
    public String getPostsOfSubreddit(@PathVariable String subreddit, Model m)
    {
        JsonNode subredditJson = redditRestTemplate.getForObject("https://oauth.reddit.com/r/" + subreddit + "/hot", JsonNode.class);
        JsonNode posts = subredditJson.findValue("data").findValue("children");
        List<DisplayPost> displayPosts = extractAndFormatPostsOfASub(posts);

        m.addAttribute("posts", displayPosts);
        m.addAttribute("subredditname", "/r/" +subreddit);
        return "posts";
    }

    public List<DisplayPost> extractAndFormatPostsOfASub(JsonNode subredditPosts) {
        int numberOfPosts = subredditPosts.size();
        List<DisplayPost> posts = new ArrayList<>();
        for (int i = 0; i < numberOfPosts; i++) {
            JsonNode current = subredditPosts.get(i).get("data");

            String url = "https://www.reddit.com" + current.findValue("permalink").toString();
            url = url.replace("\"", "");

            DisplayPost temp =
                    new DisplayPost(
                            current.findValue("title").toString(),
                            current.findValue("author").toString(),
                            url);
            posts.add(temp);
        }
        return posts;
    }
}
