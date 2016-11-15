package com.zzkun.util.web;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * 网络抓取工具类
 * Created by kun36 on 2016/7/4.
 */
@Component
public class HttpUtil {

    /**
     * 重试机制强力读取Web内容
     */
    public String readURL(String url) throws IOException {
        String result = null;
        IOException exception = null;
        for(int i = 0; i < 4; ++i) {
            try {
                result = IOUtils.toString(new URL(url), "utf8");
                if(result != null)
                    break;
            } catch (IOException e) {
                exception = e;
            }
        }
        if(result == null)
            throw exception;
        return result;
    }

}
