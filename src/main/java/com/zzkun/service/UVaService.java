package com.zzkun.service;

import com.zzkun.controller.AuthController;
import com.zzkun.dao.UVaPbInfoRepository;
import com.zzkun.dao.UVaSubmitRepository;
import com.zzkun.dao.UserRepository;
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

    @Autowired
    private ChapterManager chapterManager;

    @Autowired
    private UVaSubmitRepository uVaSubmitRepository;

    @Autowired
    private UVaPbInfoRepository uVaPbInfoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UHuntAnalyser uHuntAnalyser;

    @Autowired
    private UHuntWebGetter uHuntWebGetter;

    public List<String> getBookName() {
        Map<UHuntChapterTree, List<Integer>> bookMap = chapterManager.getBookMap();
        return bookMap.keySet().stream().map(tree -> tree.name).collect(Collectors.toList());
    }

    public List<String> getChapterName() {
        Map<UHuntChapterTree, List<Integer>> chapterMap = chapterManager.getChapterMap();
        return chapterMap.keySet().stream().map(tree -> tree.name).collect(Collectors.toList());
    }

    public List<List<Integer>> getCptCnt(List<Integer> users) {
        return __getCnt(users, chapterManager.getChapterMap());
    }

    public List<List<Integer>> getBookCnt(List<Integer> users) {
        return __getCnt(users, chapterManager.getBookMap());
    }

    /**
     * 更新所有用户的提交数据
     */
    public void flushUVaSubmit() {
        List<User> all = userRepository.findAll();
        for (User user : all) {
            List<UVaSubmit> list = uHuntWebGetter.userACSubmits(user.getUvaId());
            uVaSubmitRepository.save(list);
            logger.info("数据库用户{}AC题目数据更新完毕！", user);
        }
    }



    /**
     * 获取所有用户节点题数
     * TODO：速度较慢，待优化
     * @param users 用户uvaid列表
     * @param map 节点列表
     * @return 每个用户，每节点的做题数量
     */
    private List<List<Integer>> __getCnt(List<Integer> users, Map<UHuntChapterTree, List<Integer>> map) {
        Map<Integer, Integer> pid2Num = new HashMap<>();
        List<List<Integer>> res = new ArrayList<>();
        for (Integer user : users) {
            List<UVaSubmit> acPbs = uVaSubmitRepository.findByUvaId(user);
            Set<Integer> acNums = new HashSet<>();
            for (UVaSubmit acPb : acPbs) {
                int id = acPb.getPbId();
                if(!pid2Num.containsKey(id))
                    pid2Num.put(id, uVaPbInfoRepository.findOne(id).getNum());
                acNums.add(pid2Num.get(id));
            }
            Map<String, Integer> statistic = uHuntAnalyser.userChapterStatistic(acNums, map);
            res.add(new ArrayList<>(statistic.values()));
        }
        return res;
    }
}
