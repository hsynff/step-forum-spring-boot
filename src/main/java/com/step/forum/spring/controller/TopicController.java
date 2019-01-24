package com.step.forum.spring.controller;

import com.step.forum.spring.job.PopularTopicsUpdaterJob;
import com.step.forum.spring.model.Comment;
import com.step.forum.spring.model.Topic;
import com.step.forum.spring.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TopicController {

    @Autowired
    private TopicService topicService;
    @Autowired
    private PopularTopicsUpdaterJob popularTopicsUpdaterJob;

    @RequestMapping("/topics/{id}/comments")
    public String getCommentsByTopicId(@PathVariable("id") int id, Model model){
        List<Comment> list = topicService.getCommentsByTopicId(id);
        model.addAttribute("list", list);
        return "fragment/comments";
    }

    @RequestMapping("/topics/popular")
    @ResponseBody
    public List<Topic> getPopularTopics(){
        return popularTopicsUpdaterJob.getPopularTopics();
    }

}
