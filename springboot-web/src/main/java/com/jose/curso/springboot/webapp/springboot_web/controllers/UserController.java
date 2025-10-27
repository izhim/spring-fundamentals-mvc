package com.jose.curso.springboot.webapp.springboot_web.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.jose.curso.springboot.webapp.springboot_web.models.User;

/*  CLASS FOR THYMELEAF USE */

@Controller
public class UserController {

    @GetMapping("/details")
    public String details(Model model){
        User user = new User("Jose", "Carrillo");
        model.addAttribute("title", "Hola Mundo Cruel");
        model.addAttribute("user", user);
        return "details";
    }

    /*  USE A LIST AS ATTRIBUTE  */
    @GetMapping("/list")
    public String list(ModelMap model) {
        // List<User> users = Arrays.asList(
        //     new User("Jose", "Carrillo","carrillo@email.com"),
        //     new User("Manuel", "Benitez"),
        //     new User("Paco", "Lolo"));
        // model.addAttribute("users", users);
        model.addAttribute("title", "Hola mundo cruel");

        return "list";
    }

    /*  GENERIC ATTRIBUTE FOR DIFFERENT VIEWS  */
    @ModelAttribute("users")
    public List<User> usersModel(){
        return Arrays.asList(
            new User("Jose", "Carrillo","carrillo@email.com"),
            new User("Manuel", "Benitez"),
            new User("Paco", "Lolo"));
    }
    

}
