package com.zzkun.util.uhunt;

import com.zzkun.model.UHuntChapterTree;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * uva章节树配置文件，解析类
 * Created by kun on 2016/7/5.
 */
@Component
public class ChapterManager {

    private List<Integer> pbs; //临时数组
    private UHuntChapterTree root;

    private Map<UHuntChapterTree, List<Integer>> chapterMap;
    private Map<UHuntChapterTree, List<Integer>> bookMap;

    public ChapterManager() {
        //加载初始章节配置文件
        File file = new File(getClass().getClassLoader().getResource("uhunt/aoapc.csv").getFile());
        List<String> list = new ArrayList<>();
        try(Scanner cin = new Scanner(file)) {
            while(cin.hasNextLine())
                list.add(cin.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        parseTree(list);
    }

    /**
     * 解析Uhunt题目树
     * @param config 配置文本
     */
    public void parseTree(List<String> config) {
        root = new UHuntChapterTree(0, 0, "root", "nil");
        Map<Integer, UHuntChapterTree> nodes = new TreeMap<>();
        nodes.put(0, root);
        for(int i = 1; i < config.size(); ++i) {
            String line = config.get(i);
            String[] split = line.split(",");
            UHuntChapterTree cur = new UHuntChapterTree();
            UHuntChapterTree fa = nodes.get(Integer.parseInt(split[1]));
            cur.deep = fa.deep + 1;
            cur.id = Integer.parseInt(split[0]);
            cur.name = split[2];
            cur.type = split[3];
            fa.son.add(cur);
            nodes.put(cur.id, cur);
        }
        pbs = new ArrayList<>();
        chapterMap = null;
        bookMap = null;
    }

    public Map<UHuntChapterTree, List<Integer>> getChapterMap() {
        if(chapterMap == null) {
            pbs.clear();
            chapterMap = new TreeMap<>();
            dfsDirPb(root, 2, chapterMap);
        }
        return chapterMap;
    }

    public Map<UHuntChapterTree, List<Integer>> getBookMap() {
        if(bookMap == null) {
            pbs.clear();
            bookMap = new TreeMap<>();
            dfsDirPb(root, 1, bookMap);
        }
        return bookMap;
    }

    private void dfsDirPb(UHuntChapterTree cur, int deep, Map<UHuntChapterTree, List<Integer>> result) {
        if("uva".equals(cur.type)) {
            pbs.add(Integer.parseInt(cur.name));
            return;
        }
        for(UHuntChapterTree son : cur.son) {
            dfsDirPb(son, deep, result);
        }
        if(cur.deep == deep) {
            result.put(cur, new ArrayList<>(pbs));
            pbs.clear();
        }
    }
}
