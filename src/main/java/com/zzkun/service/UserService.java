package com.zzkun.service;

import com.zzkun.dao.UserRepo;
import com.zzkun.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

/**
 * Created by kun on 2016/7/7.
 */
@Service
public class UserService {

    @Autowired private UserRepo userRepo;

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
            if(!x.isAdmin() && x.getUvaId() != null
                    && x.getUvaId().toString().length() >= 6)
                list.add(x);
        });
        return list;
    }

    public User getUserById(Integer id) {
        return userRepo.findOne(id);
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User modifyUser(User user) {
        User pre = getUserById(user.getId());
        user.setType(pre.getType());
        user.setPassword(pre.getPassword());
        if(!hasText(user.getRealName())) user.setRealName(null);
        if(!hasText(user.getCfname())) user.setCfname(null);
        if(!hasText(user.getVjname())) user.setVjname(null);
        if(!hasText(user.getBcname())) user.setBcname(null);
        if(!hasText(user.getHduName())) user.setHduName(null);
        if(!hasText(user.getPojName())) user.setPojName(null);
        if(!hasText(user.getBlogUrl())) user.setBlogUrl(null);
        return userRepo.save(user);
    }

    public User modifyUserPassword(Integer id, String password) {
        User pre = getUserById(id);
        pre.setPassword(password);
        return userRepo.save(pre);
    }

    public void modifyUserByAdmin(User user) {
        User pre = getUserById(user.getId());
        if(hasText(user.getRealName()))
            pre.setRealName(user.getRealName());
        if(user.getUvaId() != null && user.getUvaId() > 0)
            pre.setUvaId(user.getUvaId());
        if(hasText(user.getCfname()))
            pre.setCfname(user.getCfname());
        if(hasText(user.getVjname()))
            pre.setVjname(user.getVjname());
        if(hasText(user.getBcname()))
            pre.setBcname(user.getBcname());
        if(hasText(user.getMajor()))
            pre.setMajor(user.getMajor());
        if(hasText(user.getBlogUrl()))
            pre.setBlogUrl(user.getBlogUrl());
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

    public Map<Integer, User> getUserInfoByFixedTeamList(List<FixedTeam> teamList) {
        if(teamList == null)
            return new HashMap<>();
        List<Integer> userIdList = new ArrayList<>();
        for (FixedTeam team : teamList) {
            userIdList.add(team.getUser1Id());
            userIdList.add(team.getUser2Id());
            userIdList.add(team.getUser3Id());
        }
        List<User> infos = userRepo.findAll(userIdList);
        return infos.stream().collect(Collectors.toMap(User::getId, x -> x));
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
