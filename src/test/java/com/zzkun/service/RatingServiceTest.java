package com.zzkun.service;

import com.zzkun.model.RatingRecord;
import com.zzkun.model.Training;
import jskills.Rating;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by kun on 2016/8/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class RatingServiceTest {

    @Autowired private TrainingService trainingService;

    @Autowired private RatingService ratingService;

    @Test
    public void flushTrainingUserRating() throws Exception {
        Training training = trainingService.getTrainingById(2);
        ratingService.flushTrainingUserRating(training);
    }

    @Test
    public void getTrainingUserRatingMap() throws Exception {
        Map<String, Rating> ratingMap = ratingService.getTrainingUserRatingMap(2);
        System.out.println(ratingMap);
    }

    @Test
    public void getTrainingContestPersonalRatingChangeStatus() throws Exception {
        Map<String, Pair<Rating, Rating>> map = ratingService.getTrainingContestPersonalRatingChangeStatus(2, 8);
        System.out.println(map);
    }
}