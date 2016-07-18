package com.zzkun.service;

import com.zzkun.dao.UserRepo;
import com.zzkun.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by kun on 2016/7/7.
 */
@Service
public class UserService {


    @Autowired
    private UserRepo userRepo;


    public boolean hasUser(String username) {
        return userRepo.findByUsername(username) != null;
    }

    public User valid(String username, String password) {
        User user = userRepo.findByUsername(username);
        if(user != null && user.getPassword().equals(password))
            return user;
        return null;
    }

    public boolean registerUser(User user) {
        try {
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> allNormalUsers() {
        List<User> all = userRepo.findAll();
        all.removeIf(x -> !x.getType().equals(User.Type.Normal));
        return all;
    }
}
