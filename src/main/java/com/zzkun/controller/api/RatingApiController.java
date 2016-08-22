package com.zzkun.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzkun.model.RatingRecord;
import com.zzkun.model.User;
import com.zzkun.service.RatingService;
import com.zzkun.service.UserService;
import jskills.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kun on 2016/8/17.
 */
@RestController
@RequestMapping("/api/rating")
public class RatingApiController {

    @Autowired private RatingService ratingService;
    @Autowired private UserService userService;

    @RequestMapping(value = "/training/{trainingId}/username/{name}",
            method = RequestMethod.GET,
            produces = "text/html;charset=UTF-8")
    public String trainingUser(@PathVariable Integer trainingId,
                               @PathVariable String name) {
        User user = userService.getUserByUsername(name);
        List<RatingRecord> list =
                ratingService.getPersonalRatingHistory(RatingRecord.Scope.Training, trainingId, user.getRealName());
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        result.put("result", array);
        result.put("requestTraining", trainingId);
        result.put("requestUsername", name);
        for (RatingRecord record : list) {
            if(!record.getPartIn())
                continue;
            JSONObject object = new JSONObject();
            Rating rating = new Rating(record.getMean(), record.getStandardDeviation());
            object.put("contestId", record.getContest().getId());
            object.put("contestName", record.getContest().getName());
            object.put("contestStartTime", record.getContest().getStartTime());
            object.put("contestEndTime", record.getContest().getEndTime());
            object.put("mean", record.getMean());
            object.put("standardDeviation", record.getStandardDeviation());
            object.put("conservativeRating", record.getConservativeRating());
            object.put("myRating", rating.getMyRating());
            object.put("playDuration", record.getUserPlayDuration());
            object.put("playRankSum", record.getUserRankSum());
            object.put("generateTime", record.getGenerateTime());
            array.add(object);
        }
        return result.toJSONString();
    }
}
