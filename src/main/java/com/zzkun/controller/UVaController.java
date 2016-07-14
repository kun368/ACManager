package com.zzkun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by kun on 2016/7/14.
 */
@Controller
@RequestMapping("/uva")
public class UVaController {

    @RequestMapping("/showTable")
    public String showTable() {
        return "tablefile";
    }



}
