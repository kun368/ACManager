package com.zzkun.controller;

import com.alibaba.fastjson.JSON;
import com.zzkun.dao.CptTreeRepo;
import com.zzkun.model.CptTree;
import com.zzkun.model.User;
import com.zzkun.service.ExtOjService;
import com.zzkun.util.cpt.Node;
import com.zzkun.util.cpt.NodeAnalyser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.zzkun.util.cpt.NodeBuilderKt.parseCSV;

/**
 * Created by Administrator on 2017/2/23 0023.
 */
@Controller
@RequestMapping("/cpt")
public class CptController {

    @Autowired private CptTreeRepo cptTreeRepo;
    @Autowired private NodeAnalyser nodeAnalyser;
    @Autowired private ExtOjService extOjService;


    @RequestMapping("/list")
    public String lastAssign(Model model) {
        model.addAttribute("cptList", cptTreeRepo.findAll());
        return "special_training";
    }

    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("res", cptTreeRepo.findOne(id));
        return "special_training_detail";
    }

    @RequestMapping("/rule")
    public String rule() {
        return "special_training_rule";
    }

    @RequestMapping("/statistic/{treeId}/{nodeId}")
    public String statistic(@PathVariable Integer treeId,
                            @PathVariable Integer nodeId,
                            Model model) {
        model.addAttribute("treeId", treeId);
        model.addAttribute("nodeId", nodeId);
        return "cpt_detail";
    }

    @RequestMapping("/pidsInfo/{treeId}/{nodeId}")
    public String pidsInfo(@PathVariable Integer treeId,
                           @PathVariable Integer nodeId,
                           Model model) {
        model.addAttribute("node", nodeAnalyser.getNode(treeId, nodeId));
        model.addAttribute("infoList", nodeAnalyser.getNodePbInfos(treeId, nodeId));
        return "cpt_pidsinfo";
    }

    ///// ajax

    @RequestMapping(value = "/add", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String add(@SessionAttribute(required = false) User user,
                      @RequestParam String name,
                      @RequestParam String remark,
                      @RequestParam String csvTree) {
        if (user == null || !user.isAdmin()) {
            return "没有权限...";
        }
        if (StringUtils.isBlank(csvTree)) {
            csvTree = "0,0," + name.trim() + ",LIST";
        }
        List<String> txt = new ArrayList<>(Arrays.asList(csvTree.split("\n")));
        Node rootNode = parseCSV(txt);
        String json = JSON.toJSONString(rootNode);
        CptTree tree = new CptTree(name.trim(), json, remark.trim(), user, LocalDateTime.now());
        cptTreeRepo.save(tree);
        return "添加成功！";
    }

    @RequestMapping(value = "/updatepbinfo", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updatepbinfo() {
        extOjService.flushPbInfoDB();
        return "更新完毕！";
    }

    @RequestMapping(value = "/updatepbcpt", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String updatepbcpt() {
        extOjService.flushPbInfoOfCpt();
        return "更新完毕！";
    }

}
