package com.zzkun.util.cluster;

import com.zzkun.model.Contest;
import com.zzkun.service.TrainingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * Created by Administrator on 2016/8/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class AgnesClustererTest {

    @Autowired private TrainingService trainingService;


    @Test
    public void cluster() throws Exception {
//        Contest contest = trainingService.getContest(7);
//        double[] left = trainingService.calcContestScore(contest).getLeft();
//        System.out.println(Arrays.toString(left));
//
//        for(int i = 0; i < 100; ++i) {
//            AgnesClusterer clusterer = new AgnesClusterer(left);
//            clusterer.clusterAll();
//        }
    }

}