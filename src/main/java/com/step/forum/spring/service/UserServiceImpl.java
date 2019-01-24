package com.step.forum.spring.service;

import com.step.forum.spring.model.User;
import com.step.forum.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.addUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(s);

        if (user==null){
            throw new UsernameNotFoundException(s);
        }

        return user;
    }

    @Override
    public void updateUserStatusByToken(String token) {
        userRepository.updateUserStatusByToken(token);
    }
}
