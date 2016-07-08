package com.zzkun.uhunt;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 网络抓取工具类
 * Created by kun36 on 2016/7/4.
 */
@Component
public class HttpUtil {
    public String readURL(String url) throws IOException {
        PostMethod method = new PostMethod(url);
        new HttpClient().executeMethod(method);
        return new String(method.getResponseBodyAsString().getBytes(), "utf8");
    }
}
