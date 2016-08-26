package com.zzkun.controller.api;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zzkun.model.Contest;
import com.zzkun.model.TeamRanking;
import com.zzkun.model.Training;
import com.zzkun.service.TrainingService;
import com.zzkun.util.date.MyDateFormater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contest")
public class ContestApiController {

    @Autowired private TrainingService trainingService;

    @RequestMapping(value = "/{contestId}/statistic",
            method = RequestMethod.GET,
            produces = "text/html;charset=UTF-8")
    public String statistic(@PathVariable Integer contestId) {
        Contest contest = trainingService.getContest(contestId);
        if(contest == null)
            return "";
        Training training = contest.getStage().getTraining();
        boolean[][] waClear = new boolean[contest.getRanks().size()][];
        double[] score = trainingService.calcContestScore(contest, waClear).getLeft();
        int[] rank = trainingService.calcRank(score, training);

        JSONObject object = new JSONObject(true);
        object.put("id", contest.getId());
        object.put("name", contest.getName());
        object.put("startTime", MyDateFormater.toStr1(contest.getStartTime()));
        object.put("endTime", MyDateFormater.toStr1(contest.getEndTime()));
        object.put("addTime", MyDateFormater.toStr1(contest.getAddTime()));
        object.put("problemCount", contest.getPbCnt());
        object.put("type", contest.getType());
        object.put("source", contest.getSource());
        object.put("sourceDetail", contest.getSourceDetail());
        object.put("sourceUrl", contest.getSourceUrl());
        object.put("stageId", contest.getStage().getId());
        object.put("trainingId", training.getId());
        JSONArray array = new JSONArray();
        object.put("ranking", array);
        for (TeamRanking teamRanking : contest.getRanks()) {
            JSONObject team = new JSONObject();
            team.put("account", teamRanking.getAccount());
            team.put("teamName", teamRanking.getTeamName());
            team.put("isLoaclTeam", teamRanking.getLocalTeam());
            team.put("member", teamRanking.getMember());
            team.put("solvedCount", teamRanking.getSolvedCount());
            team.put("penalty", teamRanking.calcSumPenalty());
            array.add(team);
        }
        return object.toJSONString();
    }
}
