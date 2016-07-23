package com.zzkun.service;

import com.mchange.v1.util.ArrayUtils;
import com.zzkun.dao.UserRepo;
import com.zzkun.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

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
        List<User> list = new ArrayList<>();
        all.forEach(x -> {
            if(x.getType().equals(User.Type.Normal))
                list.add(x);
        });
        return list;
    }

    public User getUserById(Integer id) {
        return userRepo.findOne(id);
    }

    public User modifyUser(User user) {
        User pre = getUserById(user.getId());
        user.setType(pre.getType());
        return userRepo.save(user);
    }

    public Map<Integer, User> getUserInfoByAssign(AssignResult result) {
        Map<Integer, User> map = new TreeMap<>();
        List<Integer> users = new ArrayList<>();
        result.getTeamList().forEach(x -> x.forEach(users::add));
        List<User> infos = userRepo.findAll(users);
        for (User info : infos)
            map.put(info.getId(), info);
        return map;
    }

    public Map<Integer, User> getUserInfoByTList(List<Training> allTraining) {
        Map<Integer, User> map = new HashMap<>();
        List<Integer> users = new ArrayList<>();
        allTraining.forEach(x -> users.add(x.getAddUid()));
        List<User> infos = userRepo.findAll(users);
        for (User info : infos)
            map.put(info.getId(), info);
        return map;
    }

    public Map<Integer, User> getUserInfoBySList(List<Stage> stageList) {
        Map<Integer, User> map = new HashMap<>();
        List<Integer> users = new ArrayList<>();
        stageList.forEach(x -> users.add(x.getAddUid()));
        List<User> infos = userRepo.findAll(users);
        for (User info : infos)
            map.put(info.getId(), info);
        return map;
    }

    public Map<Integer, User> getUserInfoByCList(List<Contest> contestList) {
        Map<Integer, User> map = new HashMap<>();
        List<Integer> users = new ArrayList<>();
        contestList.forEach(x -> users.add(x.getAddUid()));
        List<User> infos = userRepo.findAll(users);
        for (User info : infos)
            map.put(info.getId(), info);
        return map;

    }

    public int userRank(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null)
            return 0;
        if(user.getType().equals(User.Type.Normal))
            return 1;
        if(user.getType().equals(User.Type.Admin))
            return 10;
        return -1;
    }



}
