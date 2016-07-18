package com.zzkun.util.uhunt;

import com.zzkun.dao.UVaPbInfoRepo;
import com.zzkun.dao.UVaSubmitRepo;
import com.zzkun.model.UHuntChapterTree;
import com.zzkun.model.UVaSubmit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * uhunt数据分析工具类，依赖于dao
 * Created by kun on 2016/7/5.
 */
@Component
public class UHuntAnalyser {

    @Autowired private UVaSubmitRepo uVaSubmitRepo;

    @Autowired private UVaPbInfoRepo uVaPbInfoRepo;

    private final Map<Integer, Integer> pid2Num = new HashMap<>();

    /**
     * 分析用户各个节点做题数量
     * 时间复杂度：O(所有节点总题数)
     * @param acPbs 用户所有AC题目uvanum List
     * @param map 节点信息，可通过ChapterManager获得
     * @return 用户各个节点做题量
     */
    public List<Integer> userChapterStatistic(Set<Integer> acPbs, Map<UHuntChapterTree, List<Integer>> map) {
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

    /**
     * 获取所有用户节点题数
     * TODO：速度较慢，待优化
     * @param users 用户uvaid列表
     * @param map 节点列表
     * @return 每个用户，每节点的做题数量
     */
    public List<List<Integer>> getCnt(List<Integer> users, Map<UHuntChapterTree, List<Integer>> map) {
        List<List<Integer>> res = new ArrayList<>();
        Set<Integer> acNums = new HashSet<>();
        for (Integer user : users) {
            List<UVaSubmit> acPbs = uVaSubmitRepo.findByUvaId(user);
            acNums.clear();
            for (UVaSubmit acPb : acPbs) {
                int id = acPb.getPbId();
                if(!pid2Num.containsKey(id))
                    pid2Num.put(id, uVaPbInfoRepo.findOne(id).getNum());
                acNums.add(pid2Num.get(id));
            }
            List<Integer> statistic = userChapterStatistic(acNums, map);
            res.add(statistic);
        }
        return res;
    }
}
