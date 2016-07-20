package com.zzkun.controller;

import com.zzkun.service.ContestService;
import com.zzkun.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2016/7/20.
 */
@Controller
@RequestMapping("/training")
public class TrainingController {

    @Autowired private TrainingService trainingService;

    @Autowired private ContestService contestService;

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("allList", trainingService.allTraining());
        return "trainingsetlist";
    }


    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable Integer id,
                         Model model) {
        model.addAttribute("info", trainingService.getTraining(id));
        model.addAttribute("stageList", trainingService.getAllStageByTrainingId(id));
        return "stagelist";
    }

    @RequestMapping("/stage/{id}")
    public String stage(@PathVariable Integer id,
                        Model model) {
        model.addAttribute("contestList", contestService.findByStageId(id));
        return "gamelist";
    }
}
