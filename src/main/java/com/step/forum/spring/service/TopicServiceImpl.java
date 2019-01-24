package com.step.forum.spring.service;

import com.step.forum.spring.model.Comment;
import com.step.forum.spring.model.Topic;
import com.step.forum.spring.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {

    private TopicRepository topicRepository;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository){
        this.topicRepository=topicRepository;
    }

    @Override
    public List<Topic> getAllTopic() {
        return topicRepository.getAllTopic();
    }

    @Override
    public Topic getTopicById(int id) {
        return topicRepository.getTopicById(id);
    }

    @Override
    public List<Topic> getPopularTopics() {
        return topicRepository.getPopularTopics();
    }

    @Override
    public void addTopic(Topic topic) {
        topicRepository.addTopic(topic);
    }

    @Override
    public void updateTopicViewCount(int topicId) {

    }

    @Override
    public List<Topic> getSimilarTopics(String[] keywords) {
        return null;
    }

    @Override
    public List<Comment> getCommentsByTopicId(int id) {
        return topicRepository.getCommentsByTopicId(id);
    }

    @Override
    public void addComment(Comment comment) {
        topicRepository.addComment(comment);    }

    @Override
    public List<Topic> getTopicsByUserId(int idUser) {
        return topicRepository.getTopicsByUserId(idUser);
    }
}
