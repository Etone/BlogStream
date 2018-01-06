package de.hse.blogstream.twitter;

import de.hse.blogstream.webpage.DisplayPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/data")
public class DataController {


    @Autowired
    private Twitter twitter;


    @GetMapping("/posts")
    public List<DisplayPost> getPosts(){
        List<Tweet> timeline = twitter.timelineOperations().getHomeTimeline(50);

        List<DisplayPost> display = formatTimeline(timeline);
        return display;
    }

    private List<DisplayPost> formatTimeline(List<Tweet> timeline) {

        List<DisplayPost> displayablePosts = new ArrayList<>();

        for(Tweet tweet : timeline){
            displayablePosts.add(new DisplayPost(tweet.getText(), tweet.getFromUser(), ""));
        }

        return displayablePosts;
    }
}
