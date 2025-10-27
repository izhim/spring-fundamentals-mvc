package com.jose.curso.springboot.webapp.springboot_web.models.dto;

import com.jose.curso.springboot.webapp.springboot_web.models.User;

public class UserDto {

    private String title;
    private User user;

    /*  GETTERS & SETTERS  */
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    

}
