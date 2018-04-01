package com.zzkun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wzh on 2017/5/23.
 */
@Controller
@RequestMapping("/acmer")
public class AcmerInfoController {
    @RequestMapping("/infos")
    public String AcmerInfo(Model model){
        //model.addAttribute("list", ojContestService.getRecents());
        return "directionOfAcmer";
    }
}
