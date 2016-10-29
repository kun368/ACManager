package com.zzkun.controller;

import com.zzkun.model.User;
import com.zzkun.service.CFBCService;
import com.zzkun.service.ExtOjService;
import com.zzkun.service.UserService;
import com.zzkun.util.uhunt.UHuntAnalyser;
import com.zzkun.util.uhunt.UHuntTreeNode;
import com.zzkun.util.uhunt.UhuntTreeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/userac")
public class UserACController {

    private static final Logger logger = LoggerFactory.getLogger(UserACController.class);

    @Autowired private CFBCService cfbcService;
    @Autowired private UserService userService;
    @Autowired private ExtOjService extOjService;
    @Autowired private UhuntTreeManager uhuntTreeManager;
    @Autowired private UHuntAnalyser uHuntAnalyser;


    @RequestMapping("/showTable")
    public String showTable(Model model) {
        List<User> users = userService.allNormalNotNullUsers();
        List<UHuntTreeNode> bookNodes = uhuntTreeManager.getBookNodes();
        Map<Integer, List<Integer>> statistic = uHuntAnalyser.userStatistic(users, bookNodes);
        model.addAttribute("users", users);
        model.addAttribute("userACMap", extOjService.getUserPerOjACMap(users));
        model.addAttribute("bookNodes", bookNodes);
        model.addAttribute("statistic", statistic);
        model.addAttribute("cfInfoMap", cfbcService.getCFUserInfoMap());
        model.addAttribute("bcInfoMap", cfbcService.getBCUserInfoMap());
        return "tablefile_userac";
    }

    @RequestMapping("/{username}/list")
    public String list(@PathVariable String username,
                       Model model) {
        User user = userService.getUserByUsername(username);
        model.addAttribute("username", username);
        model.addAttribute("curUser", user);
        return "user_ac_detail";
    }

    @RequestMapping(value = "/updatedb", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updatedb(@SessionAttribute(required = false) User user) {
        if(user == null || !user.isAdmin())
            return "没有权限操作！";
        extOjService.flushACDB();
        return "更新完毕，久等了......";
    }
}
