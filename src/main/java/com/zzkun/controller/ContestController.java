package com.zzkun.controller;

import com.zzkun.model.Contest;
import com.zzkun.model.User;
import com.zzkun.service.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

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
    public String add1(Model model) {
        return "importComp";
    }

    @RequestMapping("/doAdd1")
    public String doAdd1(@RequestParam String contestName,
                         @RequestParam String contestType,
                         @RequestParam String vjContest,
                         @RequestParam String contestDate,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        try {
            logger.info("添加比赛1：contestName = [" + contestName + "], contestType = [" + contestType + "], vjContest = [" + vjContest + "], contestDate = [" + contestDate + "]");
            Integer stageId = (Integer) session.getAttribute("stageId");
            User user = (User) session.getAttribute("user");
            logger.info("获取到的stageid：{}, user:{}", stageId, user);
            Contest contest = trainingService.parseVj(contestName, contestType, stageId, user.getId(), vjContest, contestDate);
            session.setAttribute("contest", contest);
            return "Completeinfo";
        } catch (Exception e) {
            e.printStackTrace();
        }
        redirectAttributes.addFlashAttribute("tip", "添加失败！");
        return "redirect:/contest/add1";
    }

    @RequestMapping("/doAdd2")
    public String doAdd2(HttpServletRequest request,
                         @SessionAttribute Contest contest,
                         @SessionAttribute Integer stageId) {
        for(int i = 0; i < contest.getRanks().size(); ++i) {
            String[] split = request.getParameter("name_" + i).split(",|，");
            contest.getRanks().get(i).setMember(Arrays.asList(split));
        }
        contest = trainingService.saveContest(contest);
        logger.info("添加vj比赛请求处理完成：{}", contest);
        return "redirect:/training/stage/" + stageId;
    }
}
