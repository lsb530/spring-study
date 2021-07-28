package com.springstudy.springstudy.lombokPractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController1 {

    @RequestMapping("/")
    public String kk() {
        return "너 잘만났다";
    }

}