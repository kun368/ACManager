package com.zzkun.service;

import com.zzkun.dao.AssignResultRepo;
import com.zzkun.dao.TrainingRepo;
import com.zzkun.model.AssignResult;
import com.zzkun.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.zzkun.model.AssignResult.*;

/**
 * 随机分队工具
 * Created by kun on 2016/7/14.
 */
@Service
public class TeamAssignService {

    private static final Logger logger = LoggerFactory.getLogger(TeamAssignService.class);

    @Autowired private AssignResultRepo assignResultRepo;

    @Autowired private TrainingRepo trainingRepo;

    @Autowired private UserService userService;

    /**
     * 随机分队
     * @param users 要被分队的用户列表
     * @return 分队结果
     */
    public AssignResult assign(List<Integer> users, Integer trainingId, Type type) {
        AssignResult result = null;
        if(type.equals(Type.RANDOM)) {
            result =  randomAssign(users, trainingId);
        }
        else if(type.equals(Type.NoRepeat)) {
            result =  noRepeatAssign(users, trainingId, 7);
            if(result == null)
                result = noRepeatAssign(users, trainingId, 5);
            if(result == null)
                result = noRepeatAssign(users, trainingId, 3);
            if(result == null)
                result = noRepeatAssign(users, trainingId, 1);
            if(result == null)
                result = randomAssign(users, trainingId);
        }
        logger.info("分队结束：{}", result);
        return result;
    }

    /**
     * 分队方法：不能跟历次完全相同，两人次noRepeatLimit内不能重复组队
     */
    private AssignResult noRepeatAssign(List<Integer> users, Integer trainingId, int noRepeatLimit) {
        List<AssignResult> pre = trainingRepo.getOne(trainingId).getAssignResultList();
        Set<List<Integer>> teamSet = new HashSet<>();
        Set<List<Integer>> pairSet = new HashSet<>();
        for (AssignResult result : pre) {
            teamSet.addAll(result.getTeamList());
        }
        for(int i = pre.size()-1; i >= pre.size()-noRepeatLimit && i >= 0; --i)
            for (List<Integer> list : pre.get(i).getTeamList())
                for(int j = 0; j < list.size(); ++j)
                    for(int k = j + 1; k < list.size(); ++k)
                        pairSet.add(new ArrayList<>(Arrays.asList(list.get(j), list.get(k))));
        logger.info("历次分队set{}，PairSET：{}", teamSet, pairSet);
        for(int k = 0; k < 1024; ++k) {
            AssignResult result = randomAssign(users, trainingId);
            result.setType(Type.NoRepeat);
            boolean ok = true;
            for (List<Integer> list : result.getTeamList()) {
                if(teamSet.contains(list))
                    ok = false;
                for(int i = 0; i < list.size(); ++i) {
                    for(int j = i+1; j < list.size(); ++j) {
                        List<Integer> curPair = new ArrayList<>(Arrays.asList(list.get(i), list.get(j)));
                        if(pairSet.contains(curPair))
                            ok = false;
                    }
                }
                if(!ok) break;
            }
            if(ok) return result;
        }
        return null;
    }

    /**
     * 真正随机分队，3人一队
     * 时间复杂度：O(人数)
     * Type: RANDOM
     */
    private AssignResult randomAssign(List<Integer> users, Integer trainingId) {
        AssignResult result = new AssignResult();
        result.setType(Type.RANDOM);
        result.setTraining(trainingRepo.getOne(trainingId));

        Collections.shuffle(users);
        for(int i = 0; i < users.size(); i += 3) {
            List<Integer> team = new ArrayList<>();
            for(int j = i; j < users.size() && j < i+3; ++j)
                team.add(users.get(j));
            if(team.size() < 2) {
                List<Integer> pre = result.getTeamList().get(0);
                team.add(pre.remove(pre.size()-1));
            }
            team.sort(Integer::compareTo);
            result.getTeamList().add(team);
        }
        logger.info("真正随机分队完毕...");
        return result;
    }

    public AssignResult getLastAssign(Integer trainingId) {
        List<AssignResult> list = trainingRepo.getOne(trainingId).getAssignResultList();
        if(list != null && list.size() >= 1)
            return list.get(list.size()-1);
        return new AssignResult();
    }

    public void setAssignAccount(Integer assignId, Integer pos, String account) {
        AssignResult one = assignResultRepo.getOne(assignId);
        one.setAccount(pos, account);
        assignResultRepo.save(one);
    }

    public String exportAssign(Integer assignId) {
        logger.info("构造随机组队情况导出数据{}", assignId);
        AssignResult one = assignResultRepo.getOne(assignId);
        Map<Integer, User> infos = userService.getUserInfoByAssign(one);
        if(one.getAccountList() == null || one.getAccountList().size() != one.getTeamList().size())
            return "";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < one.getTeamList().size(); ++i) {
            StringJoiner joiner = new StringJoiner(" ");
            one.getTeamList().get(i).forEach(x -> joiner.add(infos.get(x).getRealName()));
            sb.append(one.getAccountList().get(i)).
                    append(" ").
                    append(joiner.toString()).
                    append("<br/>");
        }
        return sb.toString();
    }
}
