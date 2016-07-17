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
 * Created by kun on 2016/7/14.
 */
@Controller
@RequestMapping("/uva")
public class UVaController {

    @Autowired
    private UVaService uVaService;

    @Autowired
    private UserService userService;

    @RequestMapping("/showTable")
    public String showTable(Model model) {
        model.addAttribute("booksName", uVaService.getBookName());
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        List<List<Integer>> bookCnt = uVaService.getBookCnt(users.stream().map(User::getUvaId).collect(Collectors.toList()));
        model.addAttribute("bookCnt", bookCnt);
        return "tablefile";
    }
}
