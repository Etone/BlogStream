package de.hse.blogstream.webpage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/twitter")
public class TwitterController {

    @GetMapping("/")
    public String twitter(Model m){
        m.addAttribute("nothing");
        return "twitter";
    }
}
