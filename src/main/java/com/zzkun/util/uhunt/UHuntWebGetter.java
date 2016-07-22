package com.zzkun.util.uhunt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zzkun.model.UVaPbInfo;
import com.zzkun.model.UVaSubmit;
import com.zzkun.util.web.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * 从uhunt网站抓取数据，建议保存数据库后从数据库读
 * 后面可以刷新数据库数据
 * Created by kun36 on 2016/7/4.
 */
@Component
public class UHuntWebGetter {

    private static final Logger logger = LoggerFactory.getLogger(UHuntWebGetter.class);

    @Autowired private HttpUtil httpUtil;

    /**
     * 获取所有uva题目的信息
     * @return 题目信息List
     */
    public List<UVaPbInfo> allPbInfo() {
        List<UVaPbInfo> res = new ArrayList<>();
        String url = "http://uhunt.felix-halim.net/api/p";
        try {
            String web = httpUtil.readURL(url);
            JSONArray allPb = JSON.parseArray(web);
            for(int i = 0; i < allPb.size(); ++i) {
                JSONArray curPb = allPb.getJSONArray(i);
                res.add(UVaPbInfo.parseJSONArray(curPb));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 用户名--->uid转换
     * @param uname 用户名
     * @return uid
     */
    public int uname2uid(String uname) {
        String url = "http://uhunt.felix-halim.net/api/uname2uid/" + uname;
        try {
            return Integer.parseInt(httpUtil.readURL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 用户所有AC题目
     * @param uid 用户id
     * @return 所有AC题目list
     */
    public List<UVaSubmit> userACSubmits(int uid) {
        logger.info("开始爬取uva用户{}提交纪录", uid);
        List<UVaSubmit> res = new ArrayList<>();
        try {
            String json = httpUtil.readURL("http://uhunt.felix-halim.net/api/subs-user/" + uid);
            logger.info("爬取完毕，开始分析...");
            JSONArray subsJson = JSON.parseObject(json).getJSONArray("subs");
            Set<Integer> pidSet = new HashSet<>(); //去除重复AC题目
            for(int i = 0; i < subsJson.size(); ++i) {
                JSONArray sub = subsJson.getJSONArray(i);
                UVaSubmit submit = UVaSubmit.parseJSONArray(uid, sub);
                if(submit.getVerdictId() == 90 && !pidSet.contains(submit.getPbId())) {
                    res.add(submit);
                    pidSet.add(submit.getPbId());
                }
            }
            logger.info("分析uid用户{}所有AC题目成功，共AC题数{}", uid, res.size());
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
