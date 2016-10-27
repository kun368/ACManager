package com.zzkun.controller;

import com.zzkun.model.User;
import com.zzkun.service.ExtOjService;
import com.zzkun.service.UserService;
import com.zzkun.service.extoj.UVaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/userac")
public class UserACController {

    @Autowired private UVaService uVaService;
    @Autowired private UserService userService;
    @Autowired private ExtOjService extOjService;


    @RequestMapping("/showTable")
    public String showTable(Model model) {
        List<User> users = userService.allNormalNotNullUsers();
        model.addAttribute("users", users);
        model.addAttribute("userACMap", extOjService.getUserPerOjACMap(users));
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
}
