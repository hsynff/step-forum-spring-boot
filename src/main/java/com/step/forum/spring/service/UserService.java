package com.step.forum.spring.service;

import com.step.forum.spring.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService{
    void addUser(User user);
    @Override
    UserDetails loadUserByUsername(String s) throws UsernameNotFoundException;
}
