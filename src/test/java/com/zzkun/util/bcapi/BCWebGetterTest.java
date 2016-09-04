package com.zzkun.util.bcapi;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class BCWebGetterTest {

    @Autowired private BCWebGetter bcWebGetter;

    @Test
    public void getBCUserInfo() throws Exception {
        for(int i = 0; i < 100; ++i)
            System.out.println(bcWebGetter.getBCUserInfo("RongeRace"));
    }

}