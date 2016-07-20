package com.zzkun.controller;

import com.zzkun.model.Contest;
import com.zzkun.service.ContestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 比赛集控制器
 * Created by Administrator on 2016/7/17.
 */
@Controller
@RequestMapping("/contest")
public class ContestController {

    private static final Logger logger = LoggerFactory.getLogger(ContestController.class);

    @Autowired private ContestService contestService;


    @RequestMapping("/add1")
    public String add1(Model model) {
        model.addAttribute("groups", contestService.getAllGroup());
        return "importComp";
    }

    @RequestMapping("/doAdd1")
    public String doAdd1(@RequestParam String contestName,
                         @RequestParam String contestType,
                         @RequestParam Integer contestGroup,
                         @RequestParam String vjContest,
                         HttpSession session) {
        logger.info("添加比赛1:contestName = [" + contestName + "], contestType = [" + contestType + "], contestGroup = [" + contestGroup + "], vjContest = [" + vjContest + "]");
        Contest contest = contestService.parseVj(contestName, contestType, contestGroup, vjContest);
        session.setAttribute("contest", contest);
        return "Completeinfo";
    }

    @RequestMapping("/doAdd2")
    public String doAdd2(HttpServletRequest request,
                         @SessionAttribute Contest contest) {
        for(int i = 0; i < contest.getRanks().size(); ++i) {
            String[] split = request.getParameter("name_" + i).split(",|，");
            contest.getRanks().get(i).setMember(Arrays.asList(split));
        }
        contest = contestService.saveContest(contest);
        logger.info("添加vj比赛请求处理完成：{}", contest);
        return "redirect:/";
    }
}
