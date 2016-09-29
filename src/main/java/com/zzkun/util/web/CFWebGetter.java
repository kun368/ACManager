package com.zzkun.util.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzkun.model.CFUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by Administrator on 2016/7/31.
 */
@Component
public class CFWebGetter {

    private static final Logger logger = LoggerFactory.getLogger(CFWebGetter.class);

    @Autowired private HttpUtil httpUtil;

    public List<CFUserInfo> getUserInfos(List<String> cfnameList) {
        StringJoiner joiner = new StringJoiner(";", "http://codeforces.com/api/user.info?handles=", "");
        cfnameList.forEach(joiner::add);
        logger.info("收到获取CF用户信息请求，url：{}", joiner.toString());
        List<CFUserInfo> infoList = new ArrayList<>();
        try {
            String str = httpUtil.readURL(joiner.toString());
            JSONObject jsonObject = JSON.parseObject(str);
            if(!"OK".equals(jsonObject.getString("status")))
                return infoList;
            JSONArray result = jsonObject.getJSONArray("result");
            for(int i = 0; i < result.size(); ++i) {
                JSONObject curUser = result.getJSONObject(i);
                CFUserInfo info = new CFUserInfo(
                        curUser.getString("handle"),
                        curUser.getInteger("rating"),
                        curUser.getInteger("maxRating"),
                        curUser.getString("rank"),
                        curUser.getString("maxRank")
                );
                infoList.add(info);
            }
            return infoList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return infoList;
    }
}
