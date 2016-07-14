package com.zzkun.util.uhunt;

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

    /**
     * 分析用户各个节点做题数量
     * 时间复杂度：O(所有节点总题数)
     * @param acPbs 用户所有AC题目List
     * @param map 节点信息，可通过ChapterManager获得
     * @return 用户各个节点做题量
     */
    public Map<String, Integer> userChapterStatistic(Set<Integer> acPbs, Map<UHuntChapterTree, List<Integer>> map) {
        LinkedHashMap<String, Integer> chapterCnt = new LinkedHashMap<>();
        Set<Integer> has = new HashSet<>(); //去重set
        for (Map.Entry<UHuntChapterTree, List<Integer>> entry : map.entrySet()) {
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
