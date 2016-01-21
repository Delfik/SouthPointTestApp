package main.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by delf on 1/22/16.
 */
@Controller
public class MainController {

    @RequestMapping("/")
    // @ResponseBody
    public String index() {
        return "index.html";
    }

}
