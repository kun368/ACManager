package com.zzkun.util.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zzkun.model.ExtOjPbInfo;
import com.zzkun.model.OJType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public List<ExtOjPbInfo> allPbInfo() {
        List<ExtOjPbInfo> res = new ArrayList<>();
        String url = "http://uhunt.felix-halim.net/api/p";
        try {
            String web = httpUtil.readURL(url);
            logger.info("原始json：{}", web);
            JSONArray allPb = JSON.parseArray(web);
            for(int i = 0; i < allPb.size(); ++i) {
                JSONArray curPb = allPb.getJSONArray(i);
                ExtOjPbInfo info = new ExtOjPbInfo();
                info.setOjName(OJType.UVA);
                info.setPid(curPb.getInteger(0).toString());
                info.setNum(curPb.getInteger(1).toString());
                info.setTitle(curPb.getString(2));
                info.setDacu(curPb.getInteger(3));
                info.setMrun(curPb.getInteger(4));
                info.setMmem(curPb.getInteger(5));
                info.setNover(curPb.getInteger(6));
                info.setSube(curPb.getInteger(7));
                info.setNoj(curPb.getInteger(8));
                info.setInq(curPb.getInteger(9));
                info.setCe(curPb.getInteger(10));
                info.setRf(curPb.getInteger(11));
                info.setRe(curPb.getInteger(12));
                info.setOle(curPb.getInteger(13));
                info.setTle(curPb.getInteger(14));
                info.setMle(curPb.getInteger(15));
                info.setWa(curPb.getInteger(16));
                info.setPe(curPb.getInteger(17));
                info.setAc(curPb.getInteger(18));
                info.setRtl(curPb.getInteger(19));
                info.setStatus(curPb.getInteger(20));
                info.setRej(curPb.getInteger(21));
                info.setTotSubmit(info.calcTotSubmits());
                res.add(info);
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
    public List<Integer> userACSubmits(int uid) {
        logger.info("开始爬取uva用户{}提交纪录", uid);
        List<Integer> res = new ArrayList<>();
        try {
            String json = httpUtil.readURL("http://uhunt.felix-halim.net/api/subs-user/" + uid);
            logger.info("爬取完毕，开始分析...");
            JSONArray subsJson = JSON.parseObject(json).getJSONArray("subs");
            Set<Integer> pidSet = new HashSet<>(); //去除重复AC题目
            for(int i = 0; i < subsJson.size(); ++i) {
                JSONArray sub = subsJson.getJSONArray(i);
                int pbid = sub.getIntValue(1);
                int status = sub.getIntValue(2);
                if(status == 90 && !pidSet.contains(pbid)) {
                    res.add(pbid);
                    pidSet.add(pbid);
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
