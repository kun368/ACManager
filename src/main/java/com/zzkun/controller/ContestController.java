package com.zzkun.controller;

import com.zzkun.model.Contest;
import com.zzkun.model.User;
import com.zzkun.service.TrainingService;
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

    @RequestMapping("/add1")
    public String add1(Model model,
                       @SessionAttribute Integer stageId,
                       @SessionAttribute(required = false) User user,
                       RedirectAttributes redirectAttributes) {
        if(user == null || !user.isAdmin()) {
            redirectAttributes.addFlashAttribute("tip", "没有权限！");
            return "redirect:/training/stage/" + stageId;
        }
        return "importComp";
    }

    @RequestMapping("/importContest")
    public String importContest(@RequestParam String contestName,
                                @RequestParam String contestType,
                                @RequestParam String stTime,
                                @RequestParam String edTime,
                                @RequestParam(required = false, defaultValue = "") String myConfig,
                                @RequestParam String vjContest,
                                @SessionAttribute User user,
                                @SessionAttribute Integer stageId,
                                RedirectAttributes redirectAttributes) {
        try {
            Contest contest = trainingService.parseVj(contestName, contestType, stTime, edTime, myConfig, vjContest, user, stageId);
            contest = trainingService.saveContest(contest);
            redirectAttributes.addFlashAttribute("tip", "添加成功！");
            return "redirect:/training/stage/" + stageId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("tip", "添加失败！");
        return "redirect:/training/stage/" + stageId;
    }

    @RequestMapping("/showContest/{id}")
    public String showContest(@PathVariable Integer id,
                              Model model) {
        Contest contest = trainingService.getContest(id);
        model.addAttribute("contest", contest);
        model.addAttribute("ranks", contest.getRanks());
        return "ranklist";
    }
}
