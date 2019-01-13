package com.step.forum.spring.model;

import lombok.Data;


import java.util.List;

@Data
public class Action {

    private int id;


    private String actionType;


    private List<Role> roleList;
}
