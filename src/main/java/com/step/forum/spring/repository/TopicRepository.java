package com.step.forum.spring.repository;

import com.step.forum.spring.model.Comment;
import com.step.forum.spring.model.Topic;

import java.util.List;

public interface TopicRepository {
    List<Topic> getAllTopic();

    Topic getTopicById(int id);

    List<Topic> getPopularTopics();

    void addTopic(Topic topic);

    void updateTopicViewCount(int topicId);

    List<Topic> getSimilarTopics(String[] keywords);

    List<Comment> getCommentsByTopicId(int id);

    void addComment(Comment comment);

    List<Topic> getTopicsByUserId(int idUser);
}
