package com.zzkun.service;

import com.zzkun.model.CFUserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * Created by Administrator on 2016/8/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class CFBCServiceTest {

    @Autowired private CFBCService CFBCService;

    @Test
    public void flushCFUserInfo() throws Exception {
        CFBCService.flushCFUserInfo();
    }

    @Test
    public void getCFUserInfoMap() throws Exception {
        Map<String, CFUserInfo> map = CFBCService.getCFUserInfoMap();
        System.out.println(map);
    }
}