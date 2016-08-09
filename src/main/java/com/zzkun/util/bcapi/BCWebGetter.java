package com.zzkun.util.bcapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzkun.model.BCUserInfo;
import com.zzkun.util.web.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by kun on 2016/8/9.
 */
@Component
public class BCWebGetter {

    private static final Logger logger = LoggerFactory.getLogger(BCWebGetter.class);

    @Autowired private HttpUtil httpUtil;

    public BCUserInfo getBCUserInfo(String bcname) {
        logger.info("开始获取用户BC信息：{}", bcname);
        try {
            String str = httpUtil.readURL("http://bestcoder.hdu.edu.cn/api/api.php?type=user-rating&user=" + bcname);
            JSONArray array = JSON.parseArray(str);
            JSONObject object = array.getJSONObject(array.size() - 1);
            logger.info("获取的最后一次比赛信息：{}", object);
            return new BCUserInfo(bcname, object.getInteger("rating"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
