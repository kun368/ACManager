package com.zzkun.util.web;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.FileUtils;
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
    public String readURL(String url) throws IOException {
        return IOUtils.toString(new URL(url), "utf8");
    }
}
