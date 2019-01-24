package com.step.forum.spring.repository;

import com.step.forum.spring.model.User;

public interface UserRepository {
    void addUser(User user);
    User getUserByEmail(String email);
    void updateUserStatusByToken(String token);
}
