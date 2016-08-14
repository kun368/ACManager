package com.zzkun.service;

import com.zzkun.model.Training;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

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

}