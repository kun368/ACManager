package com.zzkun.controller;


import com.zzkun.model.User;
import com.zzkun.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by kun on 2016/7/7.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @RequestMapping("/validUsername")
    @ResponseBody
    public String validUsername(@RequestParam String name) {
        if(authService.hasUser(name)) return "false";
        return "true";
    }

    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        logger.info("收到登录请求：" + username + " " + password);
        if(authService.valid(username, password)) return "true";
        else return "false";
    }

    @RequestMapping("/rg")
    @ResponseBody
    public String rg(@RequestParam String username,
                     @RequestParam String password,
                     @RequestParam(required = false) String realName,
                     @RequestParam(required = false) Integer uvaid,
                     @RequestParam(required = false) String cfname) {
        logger.info("收到注册请求：{},{},{},{},{}", username, password, realName, uvaid, cfname);
        User user = new User(username, password, realName, uvaid, cfname);
        authService.registerUser(user);
        return "true";
    }
}
