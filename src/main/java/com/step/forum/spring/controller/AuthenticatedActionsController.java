package com.step.forum.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class AuthenticatedActionsController {

    @GetMapping("/new-topic")
    public String openNewTopicPage(){
        return "view/new-topic";
    }
}
