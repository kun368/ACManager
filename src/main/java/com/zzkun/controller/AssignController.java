package com.zzkun.controller;

import com.zzkun.model.AssignResult;
import com.zzkun.service.TrainingService;
import com.zzkun.service.UserService;
import com.zzkun.util.assign.TeamAssignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired private TeamAssignUtil teamAssignUtil;

    @Autowired private TrainingService trainingService;

    @Autowired private UserService userService;

    @RequestMapping("/listTraining/{trainingId}")
    public String list(@PathVariable Integer trainingId,
                       Model model,
                       HttpSession session) {
        model.addAttribute("userList", trainingService.getTrainingAllOkUser(trainingId));
        return "RandomTeam";
    }

    @RequestMapping(value = "/doAssign")
    public String doAssign(@RequestParam String text,
                           Model model,
                           HttpSession session) {
        List<Integer> users = new ArrayList<>();
        Arrays.asList(text.split("_")).forEach(x -> users.add(Integer.parseInt(x)));
        AssignResult assign = teamAssignUtil.assign(users, AssignResult.Type.RANDOM);
        session.setAttribute("assign", assign);
        model.addAttribute("teamList", assign.getTeamList());
        model.addAttribute("userInfo", userService.getUserInfoByAssign(assign));
        logger.info("分队结束：结果为{}", assign);
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
