package com.step.forum.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class AuthenticatedActionsController {

    @RequestMapping("/new-topic")
    public String openNewTopicPage(){
        return "redirect:/register";
    }
}

