package de.hse.blogstream.webpage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {


    @GetMapping
    public String home(Model m){
        return "home";
    }
}
