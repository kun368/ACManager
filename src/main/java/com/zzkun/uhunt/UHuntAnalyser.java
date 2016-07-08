package com.zzkun.uhunt;

import com.zzkun.model.UHuntChapterTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * uhunt数据分析工具类
 * Created by kun on 2016/7/5.
 */
@Component
public class UHuntAnalyser {

    @Autowired
    private ChapterManager chapterManager;

    public Map<String, Integer> userStatistic(Set<Integer> acPbs) {
        Map<String, Integer> chapterCnt = new LinkedHashMap<>();
        Map<UHuntChapterTree, List<Integer>> chapterMap = chapterManager.getChapterMap();
        Set<Integer> has = new TreeSet<>(); //去重set
        for (Map.Entry<UHuntChapterTree, List<Integer>> entry : chapterMap.entrySet()) {
            int cnt = 0;
            for (int pb : entry.getValue()) {
                if(acPbs.contains(pb) && !has.contains(pb)) {
                    ++cnt;
                    has.add(pb);
                }
            }
            chapterCnt.put(entry.getKey().name, cnt);
        }
        return chapterCnt;
    }
}
