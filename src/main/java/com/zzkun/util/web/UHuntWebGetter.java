package com.zzkun.util.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.zzkun.model.ExtOjPbInfo;
import com.zzkun.model.OJType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 从uhunt网站抓取数据，建议保存数据库后从数据库读
 * 后面可以刷新数据库数据
 * Created by kun36 on 2016/7/4.
 */
@Component
public class UHuntWebGetter {

    private static final Logger logger = LoggerFactory.getLogger(UHuntWebGetter.class);

    @Autowired
    private HttpUtil httpUtil;


    private ExtOjPbInfo parseJsonArray(JSONArray cur) {
        if (cur == null || cur.size() < 22)
            return null;
        ExtOjPbInfo info = new ExtOjPbInfo();
        info.setOjName(OJType.UVA);
        info.setPid(cur.getInteger(0).toString());
        info.setNum(cur.getInteger(1).toString());
        info.setTitle(cur.getString(2));
        info.setDacu(cur.getInteger(3));
        info.setMrun(cur.getInteger(4));
        info.setMmem(cur.getInteger(5));
        info.setNover(cur.getInteger(6));
        info.setSube(cur.getInteger(7));
        info.setNoj(cur.getInteger(8));
        info.setInq(cur.getInteger(9));
        info.setCe(cur.getInteger(10));
        info.setRf(cur.getInteger(11));
        info.setRe(cur.getInteger(12));
        info.setOle(cur.getInteger(13));
        info.setTle(cur.getInteger(14));
        info.setMle(cur.getInteger(15));
        info.setWa(cur.getInteger(16));
        info.setPe(cur.getInteger(17));
        info.setAc(cur.getInteger(18));
        info.setRtl(cur.getInteger(19));
        info.setStatus(cur.getInteger(20));
        info.setRej(cur.getInteger(21));
        info.setTotSubmit(info.calcTotSubmits());
        return info;
    }

    private ExtOjPbInfo parseJsonMap(LinkedHashMap<String, Object> cur) {
        JSONArray array = new JSONArray(new ArrayList<>(cur.values()));
        return parseJsonArray(array);
    }

    /**
     * 获取所有uva题目的信息
     *
     * @return 题目信息List
     */
    public List<ExtOjPbInfo> allPbInfo() {
        try {
            List<ExtOjPbInfo> res = new ArrayList<>();
            String url = "http://uhunt.felix-halim.net/api/p";
            String web = httpUtil.readURL(url);
            logger.info("原始json：{}", web);
            JSONArray allPb = JSON.parseArray(web);
            for (int i = 0; i < allPb.size(); ++i) {
                JSONArray curPb = allPb.getJSONArray(i);
                res.add(parseJsonArray(curPb));
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ExtOjPbInfo> allPbInfo2(String link) {
        List<ExtOjPbInfo> res = new ArrayList<>();
        for (int i = 1; i < 99999; ++i) {
            String url = String.format(link, Integer.toString(i));
            String web;
            try {
                web = httpUtil.readURL(url);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            LinkedHashMap<String, Object> map =
                    JSON.parseObject(web, new TypeReference<LinkedHashMap<String, Object>>() {
                    });
            ExtOjPbInfo info = parseJsonMap(map);
            if (info == null) break;
            if (!"0".equals(info.getPid()))
                res.add(info);
            if (i % 100 == 0) logger.info("PID{}解析完毕...{}", i, info);
        }
        return res;
    }

//    /**
//     * 用户名--->uid转换
//     * @param uname 用户名
//     * @return uid
//     */
//    public int uname2uid(String uname) {
//        String url = "http://uhunt.felix-halim.net/api/uname2uid/" + uname;
//        try {
//            return Integer.parseInt(httpUtil.readURL(url));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    /**
     * 用户所有AC题目
     *
     * @param uid  用户id
     * @param link
     * @return 所有AC题目list
     */
    public List<Integer> userACSubmits(Integer uid, String link) {
        if (uid == null)
            return new ArrayList<>();
        List<Integer> res = new ArrayList<>();
        try {
            String url = String.format(link, uid);
//            logger.info("开始爬取uva用户{}提交纪录:{}", uid, url);
            String json = httpUtil.readURL(url);
            logger.debug("爬取完毕，开始分析...");
            JSONArray subsJson = JSON.parseObject(json).getJSONArray("subs");
            Set<Integer> pidSet = new HashSet<>(); //去除重复AC题目
            for (int i = 0; i < subsJson.size(); ++i) {
                JSONArray sub = subsJson.getJSONArray(i);
                int pbid = sub.getIntValue(1);
                int status = sub.getIntValue(2);
                if (status == 90 && !pidSet.contains(pbid)) {
                    res.add(pbid);
                    pidSet.add(pbid);
                }
            }
            logger.info("分析uid用户{}所有AC题目成功，共AC题数{}", uid, res.size());
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
