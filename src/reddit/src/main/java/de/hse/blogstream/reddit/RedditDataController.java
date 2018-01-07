package de.hse.blogstream.reddit;

import com.fasterxml.jackson.databind.JsonNode;
import de.hse.blogstream.webpage.DisplayPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/redditdata")
public class RedditDataController {

    @Autowired
    private OAuth2RestTemplate redditRestTemplate;

    @GetMapping("/user")
    public String getUserName()
    {
        JsonNode userData = redditRestTemplate.getForObject("https://oauth.reddit.com/api/v1/me", JsonNode.class);
        JsonNode name = userData.findValue("display_name_prefixed");
        if(name == null)
        {
            name = userData.findValue("name");
        }
        System.out.println(name);
       return name.toString();
    }

    @GetMapping("/subreddits")
    public List<String> getSubscribedSubreddits()
    {
        JsonNode subredditsJson = redditRestTemplate.getForObject("https://oauth.reddit.com/subreddits/mine/subscriber", JsonNode.class);
        List<String> subredditNames = subredditsJson.findValuesAsText("display_name_prefixed");

        System.out.println(subredditNames);
        return subredditNames;
    }

    @GetMapping("/subreddit/{subreddit}")
    public List<DisplayPost> getPostsOfSubreddit(@PathVariable String subreddit)
    {
        JsonNode subredditJson = redditRestTemplate.getForObject("https://oauth.reddit.com/r/" + subreddit + "/hot", JsonNode.class);
        JsonNode posts = subredditJson.findValue("data").findValue("children");
        List<DisplayPost> displayPosts = extractAndFormatPostsOfASub(posts);

        return displayPosts;
    }

    public List<DisplayPost> extractAndFormatPostsOfASub(JsonNode subredditPosts)
    {
     int numberOfPosts = subredditPosts.size();
     List<DisplayPost> posts = new ArrayList<>();
     for(int i = 0; i < numberOfPosts; i++)
     {
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
