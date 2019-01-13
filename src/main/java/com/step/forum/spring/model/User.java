package com.step.forum.spring.model;

import lombok.Data;


@Data
public class User {

    private int id;


    private String email;


    private String password;


    private String lastName;


    private String firstName;


    private String token;


    private int status;

    private String imagePath;

    private Role role;

}
