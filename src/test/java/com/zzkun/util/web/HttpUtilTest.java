package com.zzkun.util.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class HttpUtilTest {

    @Autowired
    private HttpUtil httpUtil;

    @Test
    public void readURL() throws Exception {
        System.out.println(httpUtil.readURL("http://acm.zzkun.com"));
    }
}