package com.zzkun.service;

import com.zzkun.model.Contest;
import com.zzkun.model.RatingRecord;
import jskills.Rating;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kun on 2016/8/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class RatingServiceTest {

    @Autowired private RatingService ratingService;

    @Autowired private TrainingService trainingService;

    @Test
    public void generateRating() throws Exception {
        Contest contest = trainingService.getContest(135);
        List<Contest> contestList = new ArrayList<>();
        contestList.add(contest);
        List<RatingRecord> list = ratingService.generateRating(contestList, RatingRecord.Scope.Global, 1, RatingRecord.Type.Personal);
        System.out.println(list);
        Collections.sort(list, (o1, o2) -> {
            Rating rating1 = new Rating(o1.getMean(), o1.getStandardDeviation());
            Rating rating2 = new Rating(o2.getMean(), o2.getStandardDeviation());
            return Double.compare(rating2.getConservativeRating(), rating1.getConservativeRating());
        });
        for (RatingRecord record : list) {
            Rating rating = new Rating(record.getMean(), record.getStandardDeviation());
            System.out.println(record.getIdentifier() + ":    " + rating.getMean() + "\t " + rating.getStandardDeviation() + "\t " + rating.getConservativeRating());
        }
    }
}