package com.zzkun.controller;

import com.zzkun.model.User;
import com.zzkun.service.UVaService;
import com.zzkun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * uva相关控制器
 * Created by kun on 2016/7/14.
 */
@Controller
@RequestMapping("/uva")
public class UVaController {

    @Autowired private UVaService uVaService;

    @Autowired private UserService userService;

    @RequestMapping("/showTable")
    public String showTable(Model model) {
        model.addAttribute("booksName", uVaService.getBookName());
        model.addAttribute("cptsName", uVaService.getChapterName());
        List<User> users = userService.allNormalUsers();
        List<Integer> uvaids = users.stream().map(User::getUvaId).collect(Collectors.toList());
        model.addAttribute("users", users);
        model.addAttribute("bookCnt", uVaService.getBookCnt(uvaids));
        model.addAttribute("cptCnt", uVaService.getCptCnt(uvaids));
        return "tablefile";
    }
}
