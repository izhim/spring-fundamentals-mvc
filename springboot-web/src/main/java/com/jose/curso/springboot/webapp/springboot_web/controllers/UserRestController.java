package com.jose.curso.springboot.webapp.springboot_web.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jose.curso.springboot.webapp.springboot_web.models.User;
import com.jose.curso.springboot.webapp.springboot_web.models.dto.UserDto;


@RestController
@RequestMapping("/api")
public class UserRestController {


    /*  DATA TRANSFER OBJECT (DTO)  */
    @GetMapping(path = "/details")
    public UserDto details(){

        User user = new User("Jose", "Carrillo");
        UserDto userDto = new UserDto();
        userDto.setUser(user);
        userDto.setTitle("Hola Mundo Cruel");
        return userDto;
    }

    /*  MAPPING USE  */
    @GetMapping(path = "/details-map")
    public Map<String,Object> detailsMap(){

        User user = new User("Jose", "Carrillo");
        Map<String,Object> body = new HashMap<>();
        body.put("title", "Hola Mundo Cruel");
        body.put("user", user);
        return body;
    }

    /*  LIST USE  */
    @GetMapping("/list")
    public List<User> list(){

        User user1 = new User("Jose", "Carrillo");
        User user2 = new User("Manolo","Jimenez");
        User user3 = new User("Maria", "Cabello");

        List<User> users = Arrays.asList(user1, user2, user3);
        
        return users;
    }

}
