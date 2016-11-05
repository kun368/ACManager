package com.zzkun.controller;

import com.zzkun.model.RatingRecord;
import com.zzkun.model.User;
import com.zzkun.service.CFBCService;
import com.zzkun.service.RatingService;
import com.zzkun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

/**
 * uva相关控制器
 * Created by kun on 2016/7/14.
 */
@Controller
@RequestMapping("/statistics")
public class StatisticController {

    @Autowired private CFBCService cfbcService;
    @Autowired private UserService userService;
    @Autowired private RatingService ratingService;


    @RequestMapping("/showTable")
    public String showTable(Model model) {
        List<User> users = userService.allNormalNotNullUsers();
        model.addAttribute("users", users);
        model.addAttribute("cfInfoMap", cfbcService.getCFUserInfoMap());
        model.addAttribute("bcInfoMap", cfbcService.getBCUserInfoMap());
        model.addAttribute("ratingMap",
                ratingService.getRatingMap(RatingRecord.Scope.Global, 1, RatingRecord.Type.Personal));
        model.addAttribute("playcntMap",
                ratingService.getPlayCnt(RatingRecord.Scope.Global, 1, RatingRecord.Type.Personal));
        model.addAttribute("playDuration",
                ratingService.getPlayDuration(RatingRecord.Scope.Global, 1, RatingRecord.Type.Personal));
        return "tablefile";
    }

    @RequestMapping(value = "/updateCFBC", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updateCFBC(@SessionAttribute(required = false) User user) {
        if(user == null || !user.isAdmin())
            return "没有权限操作！";
        synchronized (this) {
            cfbcService.flushCFUserInfo();
            cfbcService.flushBCUserInfo();
        }
        return "恭喜，更新完毕！";
    }
}
