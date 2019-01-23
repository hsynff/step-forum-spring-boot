package com.step.forum.spring.controller;

import com.step.forum.spring.model.Topic;
import com.step.forum.spring.model.User;
import com.step.forum.spring.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NavigationController {

    @Autowired
    private TopicService topicService;

    @RequestMapping("/topic")
    public String topicPage(@RequestParam("id") int id, Model model){
        Topic topic = topicService.getTopicById(id);

        model.addAttribute("topic", topic);
        return "view/topic";
    }

    @RequestMapping("/login")
    public String openLoginPage(){
        return "view/login";
    }

    @GetMapping("/register")
    public String openRegisterPage(Model model){
        model.addAttribute("user", new User());
        return "view/new-account";
    }
}
