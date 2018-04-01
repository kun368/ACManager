package com.zzkun.controller.api
//hello
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.zzkun.controller.TrainingController
import com.zzkun.service.TrainingService
import com.zzkun.util.date.MyDateFormater
import com.zzkun.util.rank.RankCalculator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by kun on 2016/8/30.
 */
@RestController
@RequestMapping("/api/contest")
open class ContestApi {

    private val logger = LoggerFactory.getLogger(TrainingController::class.java)

    @Autowired val trainingService: TrainingService? = null

    @RequestMapping(value = ["/{contestId}/statistic"],
            method = [(RequestMethod.GET)],
            produces = ["text/html;charset=UTF-8"])
    fun statistic(@PathVariable contestId: Int?): String {
        logger.info("调用比赛详情API：{}", contestId)

        val contest = trainingService?.getContest(contestId) ?:
                return "contestId is InValid"
        val training = contest.stage.training
        val calculator = RankCalculator(contest, 0)
        val score = calculator.teamScore
        val rank = calculator.teamRank

        val result = JSONObject(true)
        result.put("id", contest.id)
        result.put("name", contest.name)
        result.put("startTime", MyDateFormater.toStr1(contest.startTime))
        result.put("endTime", MyDateFormater.toStr1(contest.endTime))
        result.put("addTime", MyDateFormater.toStr1(contest.addTime))
        result.put("problemCount", contest.pbCnt)
        result.put("type", contest.type)
        result.put("source", contest.source)
        result.put("sourceDetail", contest.sourceDetail)
        result.put("sourceUrl", contest.sourceUrl)
        result.put("stageId", contest.stage.id)
        result.put("trainingId", training.id)
        val array = JSONArray()
        result.put("ranking", array)
        for (i in 0..contest.ranks.size - 1) {
            val teamRanking = contest.ranks[i]
            val team = JSONObject()
            team.put("account", teamRanking.account)
            team.put("teamName", teamRanking.teamName)
            team.put("isLoaclTeam", teamRanking.localTeam)
            team.put("member", teamRanking.member)
            team.put("solvedCount", teamRanking.solvedCount)
            team.put("penalty", teamRanking.calcSumPenalty())
            team.put("score", score[i])
            team.put("rank", rank[i])
            array.add(team)
        }
        return result.toJSONString()
    }
}