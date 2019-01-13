package com.step.forum.spring.service;

import com.step.forum.spring.model.Comment;
import com.step.forum.spring.model.Topic;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopic();

    Topic getTopicById(int id);

    List<Topic> getPopularTopics();

    void addTopic(Topic topic);

    void updateTopicViewCount(int topicId);

    List<Topic> getSimilarTopics(String[] keywords);

    List<Comment> getCommentsByTopicId(int id);

    void addComment(Comment comment);

    List<Topic> getTopicByUserId(int idUser);
}
