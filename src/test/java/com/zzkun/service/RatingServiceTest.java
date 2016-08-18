package com.zzkun.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/8/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class RatingServiceTest {

    @Autowired private RatingService ratingService;

    @Test
    public void flushTrainingUserRating() throws Exception {

    }

    @Test
    public void flushGlobalUserRating() throws Exception {
        ratingService.flushGlobalUserRating();
    }

    @Test
    public void getPersonalRatingHistory() throws Exception {

    }

    @Test
    public void getPersonalRatingMap() throws Exception {

    }

}