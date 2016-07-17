package com.zzkun.controller;


import com.zzkun.model.User;
import com.zzkun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 * Created by kun on 2016/7/7.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/rg")
    public String rg() {
        return "rg";
    }

    @RequestMapping("/dologin")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        logger.info("收到登录请求：" + username + " " + password);
        User user = userService.valid(username, password);
        if(user != null) {
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            model.addAttribute("tip", "用户名或密码错误！");
            return "login";
        }
    }

    @RequestMapping("/dorg")
    public String rg(@RequestParam String username,
                     @RequestParam String password,
                     @RequestParam(required = false) String realName,
                     @RequestParam(required = false) Integer uvaid,
                     @RequestParam(required = false) String cfname,
                     Model model) {
        logger.info("收到注册请求：{},{},{},{},{}", username, password, realName, uvaid, cfname);
        if(realName.trim().isEmpty()) realName = null;
        if(cfname.trim().isEmpty()) cfname = null;
        User user = new User(username, password, realName, uvaid, cfname);
        if(userService.registerUser(user)) {
            model.addAttribute("tip", "注册成功！");
            return "index";
        } else {
            model.addAttribute("tip", "注册失败！");
            return "rg";
        }
    }

    @RequestMapping("dologout")
    public String dologout(HttpSession session) {
        logger.info("收到登出请求...");
        session.removeAttribute("user");
        return "redirect:/";
    }

    @RequestMapping("/validUsername")
    @ResponseBody
    public String validUsername(@RequestParam String name) {
        if(userService.hasUser(name)) return "false";
        return "true";
    }
}
