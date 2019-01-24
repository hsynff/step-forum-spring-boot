package com.step.forum.spring.controller;

import com.step.forum.spring.constants.TopicConstants;
import com.step.forum.spring.model.Comment;
import com.step.forum.spring.model.Topic;
import com.step.forum.spring.model.User;
import com.step.forum.spring.repository.TopicRepository;
import com.step.forum.spring.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user")
public class AuthenticatedActionsController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/new-topic")
    public String openNewTopicPage() {
        return "view/new-topic";
    }

    @PostMapping("/new-topic")
    public String addNewTopic(@RequestParam("title") String title,
                              @RequestParam("desc") String desc,
                              RedirectAttributes redirectAttributes) {
        Topic topic = new Topic();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        topic.setUser(user);
        topic.setTitle(title);
        topic.setDesc(desc);
        topic.setShareDate(LocalDateTime.now());
        topic.setViewCount(0);
        topic.setStatus(TopicConstants.TOPIC_STATUS_ACTIVE);

        topicService.addTopic(topic);
        redirectAttributes.addFlashAttribute("message", "Topic Added!");
        return "redirect:/";
    }

    @RequestMapping("/add-comment")
    @ResponseBody
    public void addComment(@RequestParam("desc")String desc,
                           @RequestParam("id-topic")int idTopic) {
        Comment comment = new Comment();
        comment.setDesc(desc);
        comment.setTopic(new Topic(idTopic));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        comment.setUser(user);
        comment.setWriteDate(LocalDateTime.now());
        topicService.addComment(comment);
    }


    @RequestMapping("/get-topics-by-user-id")
    @ResponseBody
    public List<Topic> getTopicsByUserId(){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return topicService.getTopicsByUserId(user.getId());
    }
}
