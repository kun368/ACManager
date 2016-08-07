package com.zzkun.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzkun.dao.OJContestRepo;
import com.zzkun.model.OJContest;
import com.zzkun.util.web.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
@Service
public class OJContestService {

    private static final Logger logger = LoggerFactory.getLogger(OJContestService.class);

    @Autowired private HttpUtil httpUtil;

    @Autowired private OJContestRepo ojContestRepo;

    private List<OJContest> getWebDate() {
        List<OJContest> list = new ArrayList<>();
        try {
            String str = httpUtil.readURL("http://contests.acmicpc.info/contests.json");
            JSONArray array = JSON.parseArray(str);
            for(int i = 0; i < array.size(); ++i) {
                JSONObject object = array.getJSONObject(i);
                OJContest contest = new OJContest();
                contest.setId(object.getInteger("id"));
                contest.setAccess(object.getString("access"));
                contest.setLink(object.getString("link"));
                contest.setName(object.getString("name"));
                contest.setOj(object.getString("oj"));
                contest.setStart_time(object.getString("start_time"));
                contest.setWeek(object.getString("week"));
                list.add(contest);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Scheduled(cron="0 0/30 * * * ?")
    public void flushOJContests() {
        logger.info("定时更新近期比赛开始...");
        List<OJContest> webDate = getWebDate();
        if(webDate == null || webDate.isEmpty())
            return;
        ojContestRepo.deleteAllInBatch();
        ojContestRepo.save(webDate);
    }

    public List<OJContest> getRecents() {
        List<OJContest> all = ojContestRepo.findAll();
        all.sort((x, y) -> x.getStart_time().compareTo(y.getStart_time()));
        return all;
    }
}
