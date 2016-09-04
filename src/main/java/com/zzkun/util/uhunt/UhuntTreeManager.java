package com.zzkun.util.uhunt;

import com.zzkun.model.UHuntTreeNode;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * uva章节树配置文件，解析类
 * Created by kun on 2016/7/5.
 */
@Component
public class UhuntTreeManager {

    private final List<Integer> pbs = new ArrayList<>(); //临时数组
    private UHuntTreeNode root;

    private Map<UHuntTreeNode, List<Integer>> chapterMap;
    private Map<UHuntTreeNode, List<Integer>> bookMap;

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
            cur.deep = fa.deep + 1;
            cur.id = Integer.parseInt(split[0]);
            cur.name = split[2];
            cur.type = split[3];
            fa.son.add(cur);
            nodes.put(cur.id, cur);
        }
        pbs.clear();
        chapterMap = null;
        bookMap = null;
    }

    public Map<UHuntTreeNode, List<Integer>> getChapterMap() {
        if(chapterMap == null) {
            pbs.clear();
            chapterMap = new TreeMap<>();
            dfsDirPb(root, 2, chapterMap);
        }
        return chapterMap;
    }

    public Map<UHuntTreeNode, List<Integer>> getBookMap() {
        if(bookMap == null) {
            pbs.clear();
            bookMap = new TreeMap<>();
            dfsDirPb(root, 1, bookMap);
        }
        return bookMap;
    }

    private void dfsDirPb(UHuntTreeNode cur, int deep, Map<UHuntTreeNode, List<Integer>> result) {
        if("uva".equals(cur.type)) {
            pbs.add(Integer.parseInt(cur.name));
            return;
        }
        for(UHuntTreeNode son : cur.son) {
            dfsDirPb(son, deep, result);
        }
        if(cur.deep == deep) {
            result.put(cur, new ArrayList<>(pbs));
            pbs.clear();
        }
    }
}
