package com.zzkun.controller;


import com.google.common.hash.Hashing;
import com.zzkun.model.User;
import com.zzkun.service.UserService;
import com.zzkun.util.geetest.StartCaptchaServlet;
import com.zzkun.util.geetest.VerifyLoginServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.apache.commons.lang3.StringUtils.trimToNull;


/**
 * 登陆注册认证控制器
 * Created by kun on 2016/7/7.
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired private UserService userService;

    @Autowired private StartCaptchaServlet startCaptchaServlet;

    @Autowired private VerifyLoginServlet verifyLoginServlet;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/startGeetest")
    public void startGeetest(HttpServletRequest request,
                             HttpServletResponse response) throws ServletException, IOException {
        startCaptchaServlet.doGet(request, response);
    }

    @RequestMapping("/rg")
    public String rg() {
        return "rg";
    }

    @RequestMapping("/my")
    public String my(Model model) {
        return "userdetail";
    }

    @RequestMapping("/dologin")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model,
                        HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {
        logger.info("收到登录请求：" + username + " " + password);
        if(!verifyLoginServlet.doPost(request, response)) {
            model.addAttribute("tip", "验证码验证错误！");
            return "login";
        }
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
                     @RequestParam String realName,
                     @RequestParam String major,
                     Model model,
                     HttpServletRequest request,
                     HttpServletResponse response) throws ServletException, IOException {
        logger.info("收到注册请求：{},{},{},{},{},{}", username, password, realName, major);
        if(!verifyLoginServlet.doPost(request, response)) {
            model.addAttribute("tip", "验证码验证错误！");
            return "login";
        }
        User user = new User();
        // SHA1加密密码
        password = Hashing.sha1().hashString(password, Charset.forName("utf8")).toString();
        user.setPassword(password);

        user.setUsername(trimToNull(username));
        user.setMajor(trimToNull(major));
        user.setRealName(trimToNull(realName));
        user.setType(User.Type.New);
        if(userService.registerUser(user)) {
            model.addAttribute("tip", "注册成功，请登录！");
            return "index";
        } else {
            model.addAttribute("tip", "注册失败！");
            return "rg";
        }
    }

    @RequestMapping("/doModify")
    public String doModify(User user,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        logger.info("收到修改请求：{}", user);
        userService.modifyUser(user);
        session.setAttribute("user", userService.getUserById(user.getId()));
        redirectAttributes.addFlashAttribute("tip", "信息修改成功！");
        return "redirect:/";
    }

    @RequestMapping("/doModifyUserPW")
    public String doModifyUserPW(@RequestParam Integer id,
                                 @RequestParam String password,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        logger.info("收到修改密码请求：{},{}", id, password);
        User newUser = userService.modifyUserPassword(id, password);
        session.setAttribute("user", newUser);
        redirectAttributes.addFlashAttribute("tip", "密码修改成功！");
        return "redirect:/";
    }

    @RequestMapping("/dologout")
    public String dologout(HttpSession session,
                           RedirectAttributes redirectAttributes) {
        logger.info("收到登出请求...");
        session.removeAttribute("user");
        redirectAttributes.addFlashAttribute("tip", "退出成功！");
        return "redirect:/";
    }

    //权限，入队

    @RequestMapping("/applyInACM/{id}")
    public String applyInACM(@PathVariable Integer id,
                             HttpSession session) {
        logger.info("用户申请进队：{}", id);
        User user = userService.applyInACM(id);
        session.setAttribute("user", user);
        return "redirect:/auth/my";
    }



    //ajax

    @RequestMapping(value = "/dealApplyInACM/{id}/{op}", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String dealApplyInACM(@PathVariable Integer id,
                                 @PathVariable Integer op,
                                 @SessionAttribute(required = false) User user) {
        if(user == null || !user.isAdmin()) {
            return "没有操作权限！";
        }
        userService.dealApplyInACM(id, op);
        return "操作成功！";
    }

    @RequestMapping(value = "/validUsername")
    @ResponseBody
    public String validUsername(@RequestParam String name) {
        if(userService.hasUser(name)) return "false";
        return "true";
    }

    @RequestMapping(value = "/modifyUserByAdmin",  produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String modifyUserByAdmin(User user,
                                    HttpSession session) {
        logger.info("收到管理员修改用户请求：{}", user);
        User admin = (User) session.getAttribute("user");
        if(admin == null || !admin.isAdmin())
            return "没有权限！";
        userService.modifyUserByAdmin(user);
        return "修改成功！";
    }
}
