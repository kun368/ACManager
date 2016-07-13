package com.zzkun.util.uhunt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zzkun.model.UVaPbInfo;
import com.zzkun.model.UVaSubmit;
import com.zzkun.util.web.HttpUtil;
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

    @Autowired
    private HttpUtil httpUtil;

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
        List<UVaSubmit> res = new ArrayList<>();
        try {
            String json = httpUtil.readURL("http://uhunt.felix-halim.net/api/subs-user/" + uid);
            JSONArray subsJson = JSON.parseObject(json).getJSONArray("subs");
            Set<Integer> pidSet = new TreeSet<>(); //去除重复AC题目
            for(int i = 0; i < subsJson.size(); ++i) {
                JSONArray sub = subsJson.getJSONArray(i);
                UVaSubmit submit = UVaSubmit.parseJSONArray(uid, sub);
                if(submit.getVerdictId() == 90 && !pidSet.contains(submit.getPbId())) {
                    res.add(submit);
                    pidSet.add(submit.getPbId());
                }
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
