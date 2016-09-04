package com.zzkun.controller;

import com.zzkun.model.Training;
import com.zzkun.service.RatingService;
import com.zzkun.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by kun on 2016/8/16.
 */
@Controller
@RequestMapping("/rating")
public class RatingController {

    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

    @Autowired private RatingService ratingService;
    @Autowired private TrainingService trainingService;

    @RequestMapping(value = "/updateTraining/{trainingId}", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateTraining(@PathVariable Integer trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        ratingService.flushTrainingUserRating(training);
        return "更新完毕~";
    }

    @RequestMapping(value = "/updateTrainingTeam/{trainingId}", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateTrainingTeam(@PathVariable Integer trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        ratingService.flushTrainingTeamRating(training);
        return "更新完毕~";
    }

    @RequestMapping(value = "/updateGlobal", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateGlobal() {
        ratingService.flushGlobalUserRating();
        return "更新完毕~";
    }
}
