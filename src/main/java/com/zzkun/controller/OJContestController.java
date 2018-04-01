package com.zzkun.controller;

import com.zzkun.service.OJContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "/update", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String update() {
        ojContestService.flushOJContests();
        return "更新成功！";
    }
}
