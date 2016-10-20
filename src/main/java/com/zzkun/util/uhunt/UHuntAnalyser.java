package com.zzkun.util.uhunt;

import com.zzkun.dao.ExtOjPbInfoRepo;
import com.zzkun.dao.UserACPbRepo;
import com.zzkun.model.OJType;
import com.zzkun.model.UHuntTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * uhunt数据分析工具类，依赖于dao
 * Created by kun on 2016/7/5.
 */
@Component
public class UHuntAnalyser {

    @Autowired private UserACPbRepo userACPbRepo;
    @Autowired private ExtOjPbInfoRepo extOjPbInfoRepo;

    private static final Map<Integer, Integer> pid2Num = new HashMap<>(5120);

    /**
     * 分析用户各个节点做题数量
     * 时间复杂度：O(所有节点总题数)
     * @param acPbs 用户所有AC题目uvanum List
     * @param map 节点信息，可通过ChapterManager获得
     * @return 用户各个节点做题量
     */
    public List<Integer> userChapterStatistic(Set<Integer> acPbs, Map<UHuntTreeNode, List<Integer>> map) {
        List<Integer> result = new ArrayList<>();
        Set<Integer> has = new HashSet<>(); //去重set
        for (List<Integer> list : map.values()) {
            int cnt = 0;
            for (Integer pb : list) {
                if(acPbs.contains(pb) && !has.contains(pb)) {
                    ++cnt;
                    has.add(pb);
                }
            }
            result.add(cnt);
        }
        return result;
    }

    // uvapid -> uva题号
    public int pidToNum(Integer pid) {
        if(!pid2Num.containsKey(pid)) {
            pid2Num.put(pid, Integer.parseInt(extOjPbInfoRepo.findByOjNameAndPid(OJType.UVA, pid.toString()).getNum()));
        }
        return pid2Num.get(pid);
    }
}
