package com.zzkun.controller;

import com.zzkun.config.UhuntUpdateStatus;
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
 * Created by Administrator on 2016/7/24.
 */
@Controller
@RequestMapping("/cf")
public class CFController {
    @Autowired
    private UVaService uVaService;

    @Autowired private UserService userService;

    @Autowired private UhuntUpdateStatus uhuntUpdateStatus;


    @RequestMapping("/showTable")
    public String showTable(Model model) {
        return "tablefile_cf";
    }
}
