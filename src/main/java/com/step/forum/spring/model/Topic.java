package com.step.forum.spring.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.step.forum.spring.util.TopicUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Topic {

    private int id;

    private String title;

    private String desc;


    private LocalDateTime shareDate;


    private int viewCount;

    private User user;


    private List<Comment> commentList;

    private int commentCount;


    private int status;

    public Topic(){
        commentList = new ArrayList<>();
    }

    public void addComment(Comment comment){
        commentList.add(comment);
    }

    public String topicAge(){
        return TopicUtil.ageOf(shareDate);
    }

    public Topic(int id){
        this.id = id;
    }
}
