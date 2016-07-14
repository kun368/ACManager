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
        return userRepository.findByUsername(username) != null;
    }

    public User valid(String username, String password) {
        User user = userRepository.findByUsername(username);
        if(user != null && user.getPassword().equals(password))
            return user;
        return null;
    }

    public boolean registerUser(User user) {
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
