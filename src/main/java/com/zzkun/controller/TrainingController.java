package com.zzkun.controller;

import com.alibaba.fastjson.JSON;
import com.zzkun.model.*;
import com.zzkun.service.RatingService;
import com.zzkun.service.TrainingService;
import com.zzkun.service.UserService;
import jskills.Rating;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
@Controller
@RequestMapping("/training")
public class TrainingController {

    private static final Logger logger = LoggerFactory.getLogger(TrainingController.class);

    @Autowired private TrainingService trainingService;
    @Autowired private UserService userService;
    @Autowired private RatingService ratingService;

    @RequestMapping("/list")
    public String mylist(Model model,
                         HttpSession session) {
        List<Training> allTraining = trainingService.getAllTraining();
        User user = (User) session.getAttribute("user");
        model.addAttribute("allList", allTraining);
        model.addAttribute("trainingAddUserList", userService.getUserInfoByTList(allTraining));
        model.addAttribute("ujointMap", trainingService.getUserRelativeTraining(user));
        return "trainingsetlist";
    }

    @RequestMapping("/trainingUser/{trainingId}")
    public String trainingUser(Model model,
                               @PathVariable Integer trainingId) {
        model.addAttribute("info", trainingService.getTrainingById(trainingId));
        model.addAttribute("ujoinT", trainingService.getTrainingAllUser(trainingId));
        return "trainingUser";
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
        Training training = trainingService.getTrainingById(id);
        List<Stage> stageList = training.getStageList();
        model.addAttribute("info", training);
        model.addAttribute("stageList", stageList);
        model.addAttribute("stageSizeMap", trainingService.getstageSizeMap(stageList));
        model.addAttribute("stageAddUserList", userService.getUserInfoBySList(stageList));
        session.setAttribute("trainingId", id);
        return "stagelist";
    }

    @RequestMapping("/statistic/{trainingId}")
    public String statistic(@PathVariable Integer trainingId,
                            Model model) {
        Training training = trainingService.getTrainingById(trainingId);
        model.addAttribute("info", training);
        model.addAttribute("ujoinT", trainingService.getTrainingAllOkUser(trainingId));
        model.addAttribute("ratingMap",
                ratingService.getRatingMap(RatingRecord.Scope.Training, trainingId, RatingRecord.Type.Personal));
        model.addAttribute("playcntMap",
                ratingService.getPlayCnt(RatingRecord.Scope.Training, trainingId, RatingRecord.Type.Personal));
//        model.addAttribute("averageRankMap",
//                ratingService.getAverageRank(RatingRecord.Scope.Training, trainingId, RatingRecord.Type.Personal));
        model.addAttribute("playDuration",
                ratingService.getPlayDuration(RatingRecord.Scope.Training, trainingId, RatingRecord.Type.Personal));
        return "training_statistics";
    }

    @RequestMapping("/stage/{id}")
    public String stage(@PathVariable Integer id,
                        Model model,
                        HttpSession session) {
        List<Contest> contestList = trainingService.getContestByStageId(id);
        model.addAttribute("info", trainingService.getStageById(id));
        model.addAttribute("contestList", contestList);
        model.addAttribute("trainingId", trainingService.getStageById(id).getTraining().getId());
        model.addAttribute("contestAddUserList", userService.getUserInfoByCList(contestList));
        model.addAttribute("stageId", id);
        session.setAttribute("stageId", id);
        return "gamelist";
    }

    @RequestMapping("/fixedTeam/{trainingId}")
    public String fixedTeam(@PathVariable @ModelAttribute Integer trainingId,
                            Model model) {
        Training training = trainingService.getTrainingById(trainingId);
        List<FixedTeam> teamList = training.getFixedTeamList();
        Map<Integer, User> userInfoMap = userService.getUserInfoByFixedTeamList(teamList);
        List<User> userList = trainingService.getTrainingAllOkUser(trainingId);
        Map<String, Integer> userPlayDuration =
                ratingService.getPlayDuration(RatingRecord.Scope.Training, trainingId, RatingRecord.Type.Personal);
        Map<String, Rating> userRatingMap =
                ratingService.getRatingMap(RatingRecord.Scope.Training, trainingId, RatingRecord.Type.Personal);
        model.addAttribute("info", trainingService.getTrainingById(trainingId));
        model.addAttribute("fixedList", teamList);
        model.addAttribute("userInfoMap", userInfoMap);
        model.addAttribute("userList", userList);
        model.addAttribute("userPlayDuration", userPlayDuration);
        model.addAttribute("userRatingMap", userRatingMap);
        model.addAttribute("playcntMap",
                ratingService.getPlayCnt(RatingRecord.Scope.Training, trainingId, RatingRecord.Type.Team));
        model.addAttribute("ratingMap",
                ratingService.getRatingMap(RatingRecord.Scope.Training, trainingId, RatingRecord.Type.Team));
        return "fixed";
    }

    ///////// ajax

    @RequestMapping(value = "/getstatge", produces = "text/html;charset=UTF-8" )
    @ResponseBody
    public String getstatge(@RequestParam Integer id) {
        List<Stage> stages = trainingService.getTrainingById(id).getStageList();
        String s = JSON.toJSONString(stages);
        logger.info("获取集训阶段信息：{}", s);
        return s;
    }

    @RequestMapping(value = "/doAddTraining", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String doAddTraining(@RequestParam String name,
                                @RequestParam String startDate,
                                @RequestParam String endDate,
                                @RequestParam(required = false) String remark,
                                HttpSession session) {
        logger.info("收到添加Training请求：name = [" + name + "], startDate = [" + startDate + "], endDate = [" + endDate + "], remark = [" + remark + "]");
        try {
            User user = (User) session.getAttribute("user");
            Training training = new Training();
            training.setName(name);
            training.setStartDate(LocalDate.parse(startDate));
            training.setEndDate(LocalDate.parse(endDate));
            training.setRemark(remark);
            training.setAddTime(LocalDateTime.now());
            training.setAddUid(user.getId());
            trainingService.addTraining(training);
            return "添加成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "添加失败！";
    }


    @RequestMapping(value = "/doAddStage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String doAddStage(@RequestParam String name,
                             @RequestParam String startDate,
                             @RequestParam String endDate,
                             @RequestParam(required = false) String remark,
                             @RequestParam Boolean countToRating,
                             HttpSession session) {
        logger.info("收到添加Stage请求：name = [" + name + "], startDate = [" + startDate + "], endDate = [" + endDate + "], remark = [" + remark + "], countToRating = [" + countToRating + "]");
        try {
            User user = (User) session.getAttribute("user");
            Integer trainingId = (Integer) session.getAttribute("trainingId");

            Stage stage = new Stage();
            stage.setName(name);
            stage.setStartDate(LocalDate.parse(startDate));
            stage.setEndDate(LocalDate.parse(endDate));
            stage.setRemark(remark);
            stage.setCountToRating(countToRating);
            stage.setTraining(trainingService.getTrainingById(trainingId));
            stage.setAddTime(LocalDateTime.now());
            stage.setAddUid(user.getId());
            trainingService.addStage(stage);
            return "添加成功！";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "添加失败！";
    }

    @RequestMapping(value = "/doApplyJoinTraining", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String doApplyJoinTraining(@RequestParam Integer userId,
                                      @RequestParam Integer trainingId) {
        logger.info("申请加入集训请求：userId = [" + userId + "], trainingId = [" + trainingId + "]");
        if(userId != null && trainingId != null) {
            boolean ok = trainingService.applyJoinTraining(userId, trainingId);
            if(ok) return "已收到您的申请";
            else return "加入失败！只有ACM队员可加入！";
        } else {
            return "加入失败！是否登录？";
        }
    }

    @RequestMapping(value = "/verifyUserJoin", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String verifyUserJoin(@RequestParam Integer userId,
                                 @RequestParam String op,
                                 @SessionAttribute(required = false) User user,
                                 @RequestParam Integer trainingId) {
        logger.info("审核用户加入集训请求：userId = [" + userId + "], op = [" + op + "], user = [" + user + "], trainingId = [" + trainingId + "]");
        if(user == null || trainingId == null || !user.isAdmin()) {
            return "操作失败，没有操作权限！";
        }
        trainingService.verifyUserJoin(userId, trainingId, op);
        return "操作成功！";
    }


    @RequestMapping(value = "/modifyTraining", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String modifyTraining(@SessionAttribute(required = false) User user,
                                 @RequestParam Double teamScoreRate0,
                                 Training training) {
        logger.info("修改集训：{}", training);
        if(user == null || !user.isAdmin())
            return "没有权限！";
        double sum = teamScoreRate0 + training.getTeamScoreRate1() + training.getTeamScoreRate2() + training.getTeamScoreRate3();
        logger.debug("teamScoreRate0:{}, sum:{}", teamScoreRate0, sum);
        if(Math.abs(1.0 - sum) > 0.001)
            return "Rate比例不正确！";
        try {

            training.setAddUid(user.getId());
            trainingService.modifyTraining(training);
            return "修改成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "修改失败！";
    }

    @RequestMapping(value = "/modifyStage", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String modifyStage(@SessionAttribute(required = false) User user,
                              @RequestParam Integer id,
                              @RequestParam String name,
                              @RequestParam String beginTime,
                              @RequestParam String endTime,
                              @RequestParam String remark,
                              @RequestParam Boolean countToRating) {
        logger.info("修改阶段：user = [" + user + "], id = [" + id + "], name = [" + name + "], beginTime = [" + beginTime + "], endTime = [" + endTime + "], remark = [" + remark + "]");
        if(user == null || !user.isAdmin()) {
            return "没有权限！";
        }
        try {
            trainingService.modifyStage(id, name, beginTime, endTime, remark, countToRating);
            return "修改成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "修改失败！";
    }

    @RequestMapping(value = "/{trainingId}/fixedTeam/add_modify",
            produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String fixedTeamAddModify(@PathVariable Integer trainingId,
                                     FixedTeam fixedTeam,
                                     @SessionAttribute User user) {
        logger.info("添加/修改集训固定队伍：{}, {}", trainingId, fixedTeam);
        if(user == null || !user.isAdmin()) {
            return "没有权限！";
        }
        trainingService.addOrModifyFixedTeam(trainingId, fixedTeam);
        return "操作成功！";
    }

    @RequestMapping(value = "/{trainingId}/fixedTeam/delete",
            produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String fixedTeamDelete(@RequestParam Integer fixedTeamId,
                                  @SessionAttribute User user) {
        logger.info("删除固定队伍:{}", fixedTeamId);
        if(user == null || !user.isAdmin()) {
            return "没有权限！";
        }
        trainingService.deleteFixedTeam(fixedTeamId);
        return "操作成功！";
    }
}
