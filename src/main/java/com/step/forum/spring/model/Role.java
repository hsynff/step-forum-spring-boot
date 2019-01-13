package com.step.forum.spring.model;

import lombok.Data;

import java.util.List;

@Data

public class Role {

    private int id;

    private String roleType;

    private List<Action> actionList;
}
