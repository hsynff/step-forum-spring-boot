package com.step.forum.spring.job;

import com.step.forum.spring.model.Topic;
import com.step.forum.spring.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PopularTopicsUpdaterJob {

    private TopicService topicService;
    private List<Topic> list;

    @Autowired
    public PopularTopicsUpdaterJob(TopicService topicService){
        this.topicService = topicService;
        this.list = new ArrayList<>();
    }

    @Scheduled(fixedDelay = 10 * 1000)
    private void updatePopularTopics(){
        list = topicService.getPopularTopics();
    }

    public List<Topic> getPopularTopics() {
        return list;
    }
}
