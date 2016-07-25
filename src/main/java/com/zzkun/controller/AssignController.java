package com.zzkun.controller;

import com.zzkun.model.AssignResult;
import com.zzkun.model.User;
import com.zzkun.service.TrainingService;
import com.zzkun.service.UserService;
import com.zzkun.service.TeamAssignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分队控制器
 * Created by Administrator on 2016/7/18.
 */
@Controller
@RequestMapping("/assign")
public class AssignController {

    private static final Logger logger = LoggerFactory.getLogger(AssignController.class);

    @Autowired private TeamAssignService teamAssignService;

    @Autowired private TrainingService trainingService;

    @Autowired private UserService userService;

    @RequestMapping("/lastAssign")
    public String lastAssign(Model model,
                             HttpSession session,
                             @SessionAttribute Integer trainingId) {
        AssignResult assign = teamAssignService.getLastAssign(trainingId);
        model.addAttribute("assign", assign);
        model.addAttribute("teamList", assign.getTeamList());
        model.addAttribute("userInfo", userService.getUserInfoByAssign(assign));
        return "last_teamResult";
    }

    @RequestMapping("/setAssignAccount")
    public String setAssignAccount(@RequestParam Integer assignId,
                                   @RequestParam String account,
                                   @RequestParam Integer pos,
                                   RedirectAttributes redirectAttributes) {
        logger.info("设置分队账号请求：assignId = [" + assignId + "], account = [" + account + "], pos = [" + pos + "]");
        teamAssignService.setAssignAccount(assignId, pos, account);
        redirectAttributes.addFlashAttribute("tip", "设置账号为" + account + "成功！");
        return "redirect:/assign/lastAssign";
    }

    @RequestMapping(value = "/exportAssign/{assignId}", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String exportAssign(@PathVariable Integer assignId) {
        return teamAssignService.exportAssign(assignId);
    }

    @RequestMapping("/listTraining/{trainingId}")
    public String listTraining(@PathVariable Integer trainingId,
                               Model model,
                               @SessionAttribute(required = false) User user,
                               RedirectAttributes redirectAttributes) {
        if(user == null || !user.isAdmin()) {
            redirectAttributes.addFlashAttribute("tip", "您无权进行分队！");
            return "redirect:/training/detail/" + trainingId;
        }
        model.addAttribute("userList", trainingService.getTrainingAllOkUser(trainingId));
        return "RandomTeam";
    }

    @RequestMapping(value = "/doAssign")
    public String doAssign(@RequestParam String text,
                           Model model,
                           HttpSession session,
                           @SessionAttribute Integer trainingId) {
        List<Integer> users = new ArrayList<>();
        Arrays.asList(text.split("_")).forEach(x -> users.add(Integer.parseInt(x)));
        AssignResult assign = teamAssignService.assign(users, trainingId, AssignResult.Type.NoRepeat);
        session.setAttribute("assign", assign);
        model.addAttribute("teamList", assign.getTeamList());
        model.addAttribute("userInfo", userService.getUserInfoByAssign(assign));
        return "teamResult";
    }

    @RequestMapping("/canAssign")
    @ResponseBody
    public String canAssign(@SessionAttribute AssignResult assign) {
        trainingService.saveAssign(assign);
        logger.info("分队已经被确认：{}", assign);
        return "true";
    }
}
