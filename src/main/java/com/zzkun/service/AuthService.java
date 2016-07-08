package com.zzkun.service;

import com.zzkun.dao.UserRepository;
import com.zzkun.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by kun on 2016/7/7.
 */
@Service
public class AuthService {


    @Autowired
    private UserRepository userRepository;


    public boolean hasUser(String username) {
        return userRepository.findOne(username) != null;
    }

    public boolean valid(String username, String password) {
        User user = userRepository.findOne(username);
        return user != null && user.getPassword().equals(password);
    }

    public boolean registerUser(User user) {
        if(hasUser(user.getUsername()))
            return false;
        userRepository.save(user);
        return true;
    }
}
