package com.zzkun.controller;

import com.zzkun.model.Contest;
import com.zzkun.model.User;
import com.zzkun.service.TrainingService;
import com.zzkun.util.rank.RankCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * 比赛集控制器
 * Created by Administrator on 2016/7/17.
 */
@Controller
@RequestMapping("/contest")
public class ContestController {

    private static final Logger logger = LoggerFactory.getLogger(ContestController.class);

    @Autowired private TrainingService trainingService;

    @RequestMapping("/add1/{stageId}")
    public String add1(Model model,
                       @PathVariable Integer stageId,
                       @SessionAttribute(required = false) User user,
                       RedirectAttributes redirectAttributes) {
        if(user == null || !user.isAdmin()) {
            redirectAttributes.addFlashAttribute("tip", "没有权限！");
            return "redirect:/training/stage/" + stageId;
        }
        model.addAttribute("contestId", -1);
        model.addAttribute("stageId", stageId);
        return "importComp";
    }

    @RequestMapping("/modify/{contestId}")
    public String modify1(Model model,
                          @PathVariable Integer contestId,
                          @SessionAttribute Integer stageId,
                          @SessionAttribute(required = false) User user,
                          RedirectAttributes redirectAttributes) {
        if(user == null || !user.isAdmin()) {
            redirectAttributes.addFlashAttribute("tip", "没有权限！");
            return "redirect:/training/stage/" + stageId;
        }
        Contest contest = trainingService.getContest(contestId);
        model.addAttribute("contestId", contestId);
        model.addAttribute("preContest", contest);
        model.addAttribute("stageId", stageId);
        return "importComp";
    }

    @RequestMapping("/importContest")
    public String importContest(@RequestParam Integer contestId,
                                @RequestParam String contestName,
                                @RequestParam String contestType,
                                @RequestParam String stTime,
                                @RequestParam String edTime,
                                @RequestParam String source,
                                @RequestParam String sourceDetail,
                                @RequestParam String sourceUrl,
                                @RequestParam(required = false, defaultValue = "") String myConfig,
                                @RequestParam String vjContest,
                                @RequestParam(required = false, defaultValue = "false") Boolean realContest,
                                @RequestParam Integer stageId,
                                @SessionAttribute User user,
                                RedirectAttributes redirectAttributes) {
        logger.info("导入/修改比赛。。。");
        logger.info("contestId = [" + contestId + "], contestName = [" + contestName + "], contestType = [" + contestType + "], stTime = [" + stTime + "], edTime = [" + edTime + "], source = [" + source + "], sourceDetail = [" + sourceDetail + "], sourceUrl = [" + sourceUrl + "], myConfig = [" + myConfig + "], vjContest = [" + vjContest + "], user = [" + user + "], stageId = [" + stageId + "]");
        try {
            Contest contest = trainingService.parseVj(contestName, contestType, stTime, edTime,
                    source, sourceDetail, sourceUrl, myConfig, vjContest, realContest, user, stageId);
            logger.info("比赛ID：{}", contestId);
            if(contestId == -1) {
                trainingService.saveContest(contest);
                redirectAttributes.addFlashAttribute("tip", "添加成功！");
            } else {
                Contest pre = trainingService.getContest(contestId);
                logger.info("修改比赛：原：{}", pre);
                contest.setId(contestId);
                contest.setAddTime(pre.getAddTime());
                contest.setAddUid(pre.getAddUid());
                contest.setStage(pre.getStage());
                logger.info("修改比赛：现：{}", contest);
                trainingService.saveContest(contest);
                redirectAttributes.addFlashAttribute("tip", "修改成功！");
            }
            return "redirect:/training/stage/" + stageId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("tip", "添加失败！");
        return "redirect:/training/stage/" + stageId;
    }

    @RequestMapping("/showScore/{id}")
    public String showScore(@PathVariable Integer id,
                            @RequestParam(required = false, defaultValue = "0") Integer calcType,
                            Model model) {
        Contest contest = trainingService.getContest(id);
        RankCalculator calculator = new RankCalculator(contest, calcType);
        model.addAttribute("contest", contest);
        model.addAttribute("ranks", contest.getRanks());
        model.addAttribute("sum", calculator.getTeamScore());
        model.addAttribute("pre", calculator.getPreTScore());
        model.addAttribute("myrank", calculator.getTeamRank());
        return "ranklist_score";
    }

    @RequestMapping("/contestDeleteTeam/{id}/{pos}")
    public String contestDeleteTeam(@PathVariable Integer id,
                                    @PathVariable Integer pos) {
        trainingService.deleteContestTeam(id, pos);
        return "redirect:/contest/showContest/" + id;
    }

    @RequestMapping("/deleteContest/{id}")
    public String deleteContest(@PathVariable Integer id,
                                @SessionAttribute Integer stageId) {
        logger.info("收到删除比赛请求：{}", id);
        trainingService.deleteContest(id);
        return "redirect:/training/stage/" + stageId;
    }
}
