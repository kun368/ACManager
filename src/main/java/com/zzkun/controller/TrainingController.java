package com.zzkun.controller;

import com.alibaba.fastjson.JSON;
import com.zzkun.model.Stage;
import com.zzkun.model.Training;
import com.zzkun.model.User;
import com.zzkun.service.TrainingService;
import com.zzkun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
@Controller
@RequestMapping("/training")
public class TrainingController {

    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);

    @Autowired private TrainingService trainingService;

    @Autowired private UserService userService;


    @RequestMapping("/list")
    public String mylist(Model model, HttpSession session) {
        model.addAttribute("allList", trainingService.getAllTraining());
        model.addAttribute("ujointMap", trainingService.getUserRelativeTraining((User) session.getAttribute("user")));
        return "trainingsetlist";
    }

    @RequestMapping("/AddGame")
    public String addGame(Model model) {
        model.addAttribute("allList", trainingService.getAllTraining());
        return "AddGame";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable Integer id,
                         Model model,
                         HttpSession session) {
        model.addAttribute("info", trainingService.getTrainingById(id));
        model.addAttribute("stageList", trainingService.getAllStageByTrainingId(id));
        session.setAttribute("trainingId", id);
        return "stagelist";
    }

    @RequestMapping("/stage/{id}")
    public String stage(@PathVariable Integer id,
                        Model model) {
        model.addAttribute("contestList", trainingService.getContestByStageId(id));
        model.addAttribute("trainingId", trainingService.getStageById(id).getTrainingId());
        return "gamelist";
    }

    @RequestMapping(value = "/getstatge",produces = "text/html;charset=UTF-8" )
    @ResponseBody
    public String getstatge(@RequestParam Integer id) {
        List<Stage> stages = trainingService.getAllStageByTrainingId(id);
        String s = JSON.toJSONString(stages);
        logger.info("获取集训阶段信息：{}", s);
        return s;
    }

    @RequestMapping("/doAddTraining")
    @ResponseBody
    public String doAddTraining(@RequestParam String name,
                                @RequestParam String startDate,
                                @RequestParam String endDate,
                                @RequestParam String remark) {
        logger.info("收到添加Training请求：name = [" + name + "], startDate = [" + startDate + "], endDate = [" + endDate + "], remark = [" + remark + "]");
        Training training = new Training(name, remark, startDate, endDate);
        trainingService.addTraining(training);
        return "";
    }

}
