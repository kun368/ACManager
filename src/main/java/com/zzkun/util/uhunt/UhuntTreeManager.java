package com.zzkun.util.uhunt;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * uva章节树配置文件，解析类
 * Created by kun on 2016/7/5.
 */
@Component
public class UhuntTreeManager {

    private final List<Integer> pbs = new ArrayList<>(); //临时数组
    private UHuntTreeNode root;

    private List<UHuntTreeNode> chapterMap;
    private List<UHuntTreeNode> bookMap;

    public UhuntTreeManager() {
        //加载初始章节配置文件
        File file = new File(getClass().getClassLoader().getResource("uhunt/aoapc.csv").getFile());
        try {
            List<String> lines = FileUtils.readLines(file, "utf8");
            parseTree(lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析Uhunt题目树
     * @param config 配置文本
     */
    public void parseTree(List<String> config) {
        root = new UHuntTreeNode(0, 0, "root", "nil");
        Map<Integer, UHuntTreeNode> nodes = new TreeMap<>();
        nodes.put(0, root);
        for(int i = 1; i < config.size(); ++i) {
            String line = config.get(i);
            String[] split = line.split(",");
            UHuntTreeNode cur = new UHuntTreeNode();
            UHuntTreeNode fa = nodes.get(Integer.parseInt(split[1]));
            cur.setDeep(fa.getDeep() + 1);
            cur.setId(Integer.parseInt(split[0]));
            cur.setName(split[2]);
            cur.setType(split[3]);
            fa.getSon().add(cur);
            nodes.put(cur.getId(), cur);
        }
        pbs.clear();
        chapterMap = null;
        bookMap = null;
    }

    public List<UHuntTreeNode> getCptNodes() {
        if(chapterMap == null) {
            pbs.clear();
            chapterMap = new ArrayList<>();
            dfsDirPb(root, 2, chapterMap);
        }
        return chapterMap;
    }

    public List<UHuntTreeNode> getBookNodes() {
        if(bookMap == null) {
            pbs.clear();
            bookMap = new ArrayList<>();
            dfsDirPb(root, 1, bookMap);
        }
        return bookMap;
    }

    private void dfsDirPb(UHuntTreeNode cur, int deep, List<UHuntTreeNode> result) {
        if("uva".equals(cur.getType())) {
            pbs.add(Integer.parseInt(cur.getName()));
            return;
        }
        for(UHuntTreeNode son : cur.getSon()) {
            dfsDirPb(son, deep, result);
        }
        if(cur.getDeep() == deep) {
            cur.getPids().clear();
            cur.getPids().addAll(pbs);
            result.add(cur);
            pbs.clear();
        }
    }
}
