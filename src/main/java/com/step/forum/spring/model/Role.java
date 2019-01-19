package com.step.forum.spring.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Role {
    private int id;
    private String roleType;
    private List<Action> actionList;

    public Role(int id) {
        this.id = id;
    }
}
