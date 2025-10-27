package com.jose.curso.springboot.webapp.springboot_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    /*  REDIRECT TO LIST REFRESHING DATA  */
    @GetMapping({"", "/", "/home"})
    public String home() {
        return "redirect:/list";    //  changes the url path, restarts the request and refreshes losing params
        // return "forward:/list";  ->  keeps the same http request but launches other controller action
    }
    

}
