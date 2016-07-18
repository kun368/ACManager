package com.zzkun.service;

import com.zzkun.dao.UVaSubmitRepo;
import com.zzkun.dao.UserRepo;
import com.zzkun.model.UHuntChapterTree;
import com.zzkun.model.UVaSubmit;
import com.zzkun.model.User;
import com.zzkun.util.uhunt.ChapterManager;
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

    @Autowired private ChapterManager chapterManager;

    @Autowired private UVaSubmitRepo uVaSubmitRepo;

    @Autowired private UserRepo userRepo;

    @Autowired private UHuntWebGetter uHuntWebGetter;

    @Autowired private UHuntAnalyser uHuntAnalyser;

    public List<String> getBookName() {
        return chapterManager.getBookMap().keySet().stream().map(x -> x.name).collect(Collectors.toList());
    }

    public List<String> getChapterName() {
        return chapterManager.getChapterMap().keySet().stream().map(x -> x.name).collect(Collectors.toList());
    }

    public List<List<Integer>> getCptCnt(List<Integer> users) {
        return uHuntAnalyser.getCnt(users, chapterManager.getChapterMap());
    }

    public List<List<Integer>> getBookCnt(List<Integer> users) {
        return uHuntAnalyser.getCnt(users, chapterManager.getBookMap());
    }

    /**
     * 更新所有用户的提交数据
     */
    public void flushUVaSubmit() {
        List<User> all = userRepo.findAll();
        for (User user : all) {
            List<UVaSubmit> list = uHuntWebGetter.userACSubmits(user.getUvaId());
            uVaSubmitRepo.save(list);
            logger.info("数据库用户{}AC题目数据更新完毕！", user);
        }
    }
}
