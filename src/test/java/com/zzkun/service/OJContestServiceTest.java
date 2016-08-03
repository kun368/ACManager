package com.zzkun.service;

import com.zzkun.model.OJContest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/8/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class OJContestServiceTest {

    @Autowired private OJContestService ojContestService;

    @Test
    public void getRecents() throws Exception {
        List<OJContest> recents = ojContestService.getRecents();
        System.out.println(recents);
    }
}