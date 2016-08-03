package com.zzkun.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzkun.model.OJContest;
import com.zzkun.util.web.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
@Service
public class OJContestService {

    @Autowired private HttpUtil httpUtil;


    public List<OJContest> getRecents() {
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

}
