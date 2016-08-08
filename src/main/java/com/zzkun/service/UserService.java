package com.zzkun.service;

import com.mchange.v1.util.ArrayUtils;
import com.zzkun.dao.UserRepo;
import com.zzkun.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public List<User> allUser() {
        return userRepo.findAll();
    }

    public List<User> allNormalNotNullUsers() {
        List<User> all = userRepo.findAll();
        List<User> list = new ArrayList<>();
        all.forEach(x -> {
            if(!User.Type.Admin.equals(x.getType())
                    && x.getUvaId() != null
                    && x.getUvaId().toString().length() >= 6)
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
        user.setPassword(pre.getPassword());
        if(user.getRealName().trim().isEmpty())
            user.setRealName(null);
        if(user.getCfname().trim().isEmpty())
            user.setCfname(null);
        if(user.getVjname().trim().isEmpty())
            user.setVjname(null);
        return userRepo.save(user);
    }

    public User modifyUserPassword(Integer id, String password) {
        User pre = getUserById(id);
        pre.setPassword(password);
        return userRepo.save(pre);
    }

    public void modifyUserByAdmin(User user) {
        User pre = getUserById(user.getId());
        if(StringUtils.hasLength(user.getRealName()))
            pre.setRealName(user.getRealName());
        if(user.getUvaId() != null && user.getUvaId() > 0)
            pre.setUvaId(user.getUvaId());
        if(StringUtils.hasLength(user.getCfname()))
            pre.setCfname(user.getCfname());
        if(StringUtils.hasLength(user.getVjname()))
            pre.setVjname(user.getVjname());
        if(StringUtils.hasLength(user.getMajor()))
            pre.setMajor(user.getMajor());
        if(user.getType() != null)
            pre.setType(user.getType());
        userRepo.save(pre);
    }

    public Map<Integer, User> getUserInfoByAssign(AssignResult result) {
        Map<Integer, User> map = new TreeMap<>();
        if(result == null) return map;
        List<Integer> users = new ArrayList<>();
        result.getTeamList().forEach(x -> x.forEach(users::add));
        List<User> infos = userRepo.findAll(users);
        for (User info : infos)
            map.put(info.getId(), info);
        return map;
    }

    public Map<Integer, User> getUserInfoByTList(List<Training> allTraining) {
        Map<Integer, User> map = new HashMap<>();
        if(allTraining == null) return map;
        List<Integer> users = new ArrayList<>();
        allTraining.forEach(x -> users.add(x.getAddUid()));
        List<User> infos = userRepo.findAll(users);
        for (User info : infos)
            map.put(info.getId(), info);
        return map;
    }

    public Map<Integer, User> getUserInfoBySList(List<Stage> stageList) {
        Map<Integer, User> map = new HashMap<>();
        if(stageList == null) return map;
        List<Integer> users = new ArrayList<>();
        stageList.forEach(x -> users.add(x.getAddUid()));
        List<User> infos = userRepo.findAll(users);
        for (User info : infos)
            map.put(info.getId(), info);
        return map;
    }

    public Map<Integer, User> getUserInfoByCList(List<Contest> contestList) {
        Map<Integer, User> map = new HashMap<>();
        if(contestList == null) return map;
        List<Integer> users = new ArrayList<>();
        contestList.forEach(x -> users.add(x.getAddUid()));
        List<User> infos = userRepo.findAll(users);
        for (User info : infos)
            map.put(info.getId(), info);
        return map;
    }

    public Map<Integer, User> getUserInfoByFixedTList(List<FixedTeam> teamList) {
        Map<Integer, User> map = new HashMap<>();
        if(teamList == null) return map;
        Set<Integer> users = new HashSet<>();
        teamList.forEach(x -> users.addAll(x.getUids()));
        List<User> infos = userRepo.findAll();
        for (User info : infos) {
            if(users.contains(info.getId()))
                map.put(info.getId(), info);
        }
        return map;
    }

    public User applyInACM(Integer id) {
        User one = userRepo.findOne(id);
        if(one == null) return null;
        one.setType(User.Type.Verifying);
        return userRepo.save(one);
    }

    public void dealApplyInACM(Integer id, Integer op) {
        User one = userRepo.findOne(id);
        if(one == null || one.isAdmin())
            return;
        if(op == 1) {
            one.setType(User.Type.Acmer);
        } else if(op == 0) {
            one.setType(User.Type.Reject);
        }
        userRepo.save(one);
    }



}
