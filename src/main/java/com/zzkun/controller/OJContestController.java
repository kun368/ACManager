package com.zzkun.controller;

import com.zzkun.service.OJContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/8/3.
 */
@Controller
@RequestMapping("/oj")
public class OJContestController {

    @Autowired private OJContestService ojContestService;

    @RequestMapping("/recentContest")
    public String recentContest(Model model) {
        model.addAttribute("list", ojContestService.getRecents());
        return "oj_recent_contest";
    }

}
