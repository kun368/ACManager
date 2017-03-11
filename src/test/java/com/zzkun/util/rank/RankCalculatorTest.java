package com.zzkun.util.rank;

import com.zzkun.service.TrainingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kun on 2016/8/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class RankCalculatorTest {

    @Autowired private TrainingService trainingService;

    @Test
    public void calcAll() throws Exception {
//        Contest contest = trainingService.getContest(155);
//        RankCalculator calculator = new RankCalculator(contest);
//        calculator.calcAll();
//        double[] teamScore = calculator.getTeamScore();
//        int[] teamRank = calculator.getTeamRank();
//        System.out.println(Arrays.toString(teamScore));
//        System.out.println(Arrays.toString(teamRank));
    }

}