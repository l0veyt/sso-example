package com.l0veyt.example.ssoserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {



    @GetMapping("/")
    public String login() {
        return "login";
    }

    @PostMapping("/loginByPwd")
    public String loginBy(String username, String password, String originUrl) {
        return "";
    }
}
