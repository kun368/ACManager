package com.zzkun.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzkun.model.Contest;
import com.zzkun.model.Stage;
import com.zzkun.model.Training;
import com.zzkun.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by kun on 2016/8/25.
 */
@RestController
@RequestMapping("/api/training")
public class TrainingApiController {

    @Autowired private TrainingService trainingService;

    @RequestMapping(value = "/{trainingId}/list",
            method = RequestMethod.GET,
            produces = "text/html;charset=UTF-8")
    public String list(@PathVariable Integer trainingId) {
        Training training = trainingService.getTrainingById(trainingId);
        if(training == null)
            return "";
        List<Stage> stageList = training.getStageList();

        JSONObject object = new JSONObject(true);
        object.put("id", training.getId());
        object.put("name", training.getName());
        object.put("startDate", training.getStartDate());
        object.put("endDate", training.getEndDate());
        object.put("addTime", training.getAddTime());
        JSONArray stageArray = new JSONArray();
        object.put("stageList", stageArray);
        for (Stage stage : stageList) {
            JSONObject stageObject = new JSONObject(true);
            stageObject.put("id", stage.getId());
            stageObject.put("name", stage.getName());
            stageObject.put("startDate", stage.getStartDate());
            stageObject.put("endDate", stage.getEndDate());
            JSONArray contestIdArray = new JSONArray();
            stageObject.put("contestIdList", contestIdArray);
            for (Contest contest : stage.getContestList())
                contestIdArray.add(contest.getId());
            stageArray.add(stageObject);
        }
        return object.toJSONString();
    }

}
