package com.zzkun.service;

import com.zzkun.dao.UVaPbInfoRepo;
import com.zzkun.dao.UVaSubmitRepo;
import com.zzkun.dao.UserRepo;
import com.zzkun.model.UVaPbInfo;
import com.zzkun.model.UVaSubmit;
import com.zzkun.model.User;
import com.zzkun.util.uhunt.UhuntTreeManager;
import com.zzkun.util.uhunt.UHuntAnalyser;
import com.zzkun.util.uhunt.UHuntWebGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by kun on 2016/7/14.
 */
@Service
public class UVaService {

    private static Logger logger = LoggerFactory.getLogger(UVaService.class);

    @Autowired private UhuntTreeManager uhuntTreeManager;

    @Autowired private UVaSubmitRepo uVaSubmitRepo;

    @Autowired private UVaPbInfoRepo uVaPbInfoRepo;

    @Autowired private UserRepo userRepo;

    @Autowired private UHuntWebGetter uHuntWebGetter;

    @Autowired private UHuntAnalyser uHuntAnalyser;

    public List<String> getBookName() {
        return uhuntTreeManager.getBookMap().keySet().stream().map(x -> x.name).collect(Collectors.toList());
    }

    public List<String> getChapterName() {
        return uhuntTreeManager.getChapterMap().keySet().stream().map(x -> x.name).collect(Collectors.toList());
    }

    public List<List<Integer>> getCptCnt(List<Integer> users) {
        return uHuntAnalyser.getCnt(users, uhuntTreeManager.getChapterMap());
    }

    public List<List<Integer>> getBookCnt(List<Integer> users) {
        return uHuntAnalyser.getCnt(users, uhuntTreeManager.getBookMap());
    }

    /**
     * 更新所有用户的提交数据
     */
    public void flushUVaSubmit() {
        List<User> userList = userRepo.findAll();
        List<UVaSubmit> all = new ArrayList<>();
        for (User user : userList) {
            if(user.getUvaId() != null)
                all.addAll(uHuntWebGetter.userACSubmits(user.getUvaId()));
        }
        uVaSubmitRepo.save(all);
        logger.info("数据库用户AC题目数据更新完毕！");
    }

    /**
     * 更新uva题目信息
     */
    public void flushUVaPbInfo() {
        List<UVaPbInfo> uVaPbInfos = uHuntWebGetter.allPbInfo();
        uVaPbInfoRepo.save(uVaPbInfos);
    }
}
