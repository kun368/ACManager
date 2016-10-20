package com.zzkun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kun on 2016/10/20.
 */
@Controller
@RequestMapping("/userac")
public class UserACController {

    @RequestMapping("/{username}/list")
    public String list(@PathVariable String username,
                       Model model) {
        model.addAttribute("username", username);
        return "user_ac_detail";
    }
}
