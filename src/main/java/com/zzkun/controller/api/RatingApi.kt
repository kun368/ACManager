package com.zzkun.controller.api

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.zzkun.model.RatingRecord
import com.zzkun.service.RatingService
import com.zzkun.service.UserService
import jskills.Rating
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by kun on 2016/8/30.
 */
@RestController
@RequestMapping("/api/rating")
open class RatingApi(
        @Autowired private val ratingService: RatingService,
        @Autowired private val userService: UserService) {

    @RequestMapping(value = "/training/{trainingId}/username/{name}", 
            method = arrayOf(RequestMethod.GET), 
            produces = arrayOf("text/html;charset=UTF-8"))
    fun trainingUser(@PathVariable trainingId: Int?,
                     @PathVariable name: String): String {
        val user = userService.getUserByUsername(name)
        val list = ratingService.getPersonalRatingHistory(RatingRecord.Scope.Training, trainingId, user?.realName)
        val result = JSONObject()
        val array = JSONArray()
        result["result"] = array
        result["requestTraining"] = trainingId
        result["requestUsername"] = name
        if(list != null) {
            for ( record in list) {
                if (!record.partIn)
                    continue
                val recordObj = JSONObject()
                val rating = Rating(record.mean!!, record.standardDeviation!!)
                recordObj.put("contestId", record.contest.id)
                recordObj.put("contestName", record.contest.name)
                recordObj.put("contestStartTime", record.contest.startTime)
                recordObj.put("contestEndTime", record.contest.endTime)
                recordObj.put("mean", record.mean)
                recordObj.put("standardDeviation", record.standardDeviation)
                recordObj.put("conservativeRating", record.conservativeRating)
                recordObj.put("myRating", rating.myRating)
                recordObj.put("playDuration", record.userPlayDuration)
                recordObj.put("playRankSum", record.userRankSum)
                recordObj.put("generateTime", record.generateTime)
                array.add(recordObj)
            }
        }
        return result.toJSONString()
    }
}