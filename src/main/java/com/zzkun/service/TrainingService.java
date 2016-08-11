package com.zzkun.service;

import com.zzkun.dao.*;
import com.zzkun.model.*;
import com.zzkun.util.cluster.AgnesClusterer;
import com.zzkun.util.stder.DataStder;
import com.zzkun.util.stder.RawData;
import com.zzkun.util.vjudge.VJRankParser;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Administrator on 2016/7/20.
 */
@Service
public class TrainingService {

    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class);

    @Autowired private ContestRepo contestRepo;

    @Autowired private StageRepo stageRepo;

    @Autowired private TrainingRepo trainingRepo;

    @Autowired private UJoinTRepo uJoinTRepo;

    @Autowired private UserRepo userRepo;

    @Autowired private AssignResultRepo assignResultRepo;

    @Autowired private FixedTeamRepo fixedTeamRepo;

    @Autowired private VJRankParser vjRankParser;

    @Autowired private DataStder dataStder;


    //// Training

    public List<Training> getAllTraining() {
        return trainingRepo.findAll();
    }

    public Training getTrainingById(Integer id) {
        return trainingRepo.findOne(id);
    }

    public Training getTrainingByContestId(Integer contestId) {
        return getContest(contestId).getStage().getTraining();
    }

    public Map<Integer, UJoinT> getUserRelativeTraining(User user) {
        Map<Integer, UJoinT> result = new HashMap<>();
        if(user == null)
            return result;
        List<UJoinT> list = user.getuJoinTList();
        list.forEach(x -> result.put(x.getTraining().getId(), x));
        logger.info("获取用户参加的所有集训信息：{}", result);
        return result;
    }

    public void addTraining(Training training) {
        trainingRepo.save(training);
    }

    public List<User> getTrainingAllOkUser(Integer trainingId) {
        List<UJoinT> all = getTrainingById(trainingId).getuJoinTList();
        List<Integer> uids = new ArrayList<>();
        all.forEach(x -> {
            if(x.getStatus().equals(UJoinT.Status.Success))
                uids.add(x.getUser().getId());
        });
        return userRepo.findAll(uids);
    }

    public Map<User, String> getTrainingAllUser(Integer trainingId) {
        List<UJoinT> all = trainingRepo.findOne(trainingId).getuJoinTList();
        List<User> userList = userRepo.findAll();
        Map<Integer, User> userMap = new HashMap<>();
        userList.forEach(x -> userMap.put(x.getId(), x));
        Map<User, String> map = new LinkedHashMap<>();
        all.forEach(x -> map.put(userMap.get(x.getUser().getId()), x.getStatus().name()));
        logger.info("查询集训所有用户情况：size{}", map.size());
        return map;
    }

    public void modifyTraining(Integer id, String name,
                               String beginTime, String endTime, String remark,
                               Double standard, Double expand,
                               Double mergeLimit, Integer waCapcity) {
        Training training = getTrainingById(id);
        if(StringUtils.hasText(name))
            training.setName(name);
        if(StringUtils.hasText(remark))
            training.setRemark(remark);
        training.setStartDate(LocalDate.parse(beginTime));
        training.setEndDate(LocalDate.parse(endTime));
        training.setStandard(standard);
        training.setExpand(expand);
        training.setMergeLimit(mergeLimit);
        training.setWaCapcity(waCapcity);
        trainingRepo.save(training);
    }

    public Map<Integer, Integer> trainingSizeMap(List<Training> allTraining) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Training training : allTraining) {
            map.put(training.getId(), training.getStageList().size());
        }
        return map;
    }

    //用户申请参加集训
    public void applyJoinTraining(Integer userId, Integer trainingId) {
        User user = userRepo.findOne(userId);
        Training training = trainingRepo.findOne(trainingId);
        if(user == null || !user.isACMer()) //管理员不能加入集训
            return;
        UJoinT uJoinT = uJoinTRepo.findByUserAndTraining(user, training);
        if(uJoinT != null) {
            uJoinT.setStatus(UJoinT.Status.Pending);
        } else {
            uJoinT = new UJoinT(user, training, UJoinT.Status.Pending);
        }
        uJoinTRepo.save(uJoinT);
    }

    public void verifyUserJoin(Integer userId, Integer trainingId, String op) {
        User user = userRepo.findOne(userId);
        Training training = trainingRepo.findOne(trainingId);
        UJoinT uJoinT = uJoinTRepo.findByUserAndTraining(user, training);
        if("true".equals(op)) {
            uJoinT.setStatus(UJoinT.Status.Success);
        } else if("false".equals(op)) {
            uJoinT.setStatus(UJoinT.Status.Reject);
        }
        uJoinTRepo.save(uJoinT);
    }

    ///// fixedTeam
    public List<FixedTeam> getAllTrainingFixedTeam(Integer trainingId) {
        return fixedTeamRepo.findByTrainingId(trainingId);
    }

    //// Stage

    public Stage getStageById(Integer id) {
        return stageRepo.findOne(id);
    }

    public void addStage(Stage stage) {
        stageRepo.save(stage);
    }

    public void modifyStage(Integer id, String name, String beginTime, String endTime, String remark) {
        Stage stage = getStageById(id);
        if(StringUtils.hasText(name))
            stage.setName(name);
        if(StringUtils.hasText(remark))
            stage.setRemark(remark);
        stage.setStartDate(LocalDate.parse(beginTime));
        stage.setEndDate(LocalDate.parse(endTime));
        stageRepo.save(stage);
    }

    public Map<Integer, Integer> getstageSizeMap(List<Stage> stageList) {
        Map<Integer, Integer> map = new HashMap<>(stageList.size());
        for (Stage stage : stageList) {
            Integer stageId = stage.getId();
            map.put(stageId, (int) contestRepo.countByStageId(stageId));
        }
        return map;
    }

    //// Contest

    public Contest saveContest(Contest contest) {
        return contestRepo.save(contest);
    }

    public Contest getContest(Integer id) {
        return contestRepo.findOne(id);
    }

    public List<Contest> getContestByStageId(Integer id) {
        return contestRepo.findByStageId(id);
    }

    public void deleteContestTeam(Integer contestId, Integer pos) {
        Contest one = contestRepo.findOne(contestId);
        logger.info("删除比赛{}的队伍{}", contestId, pos);
        if(pos < one.getRanks().size()) {
            one.getRanks().remove(pos.intValue());
        }
        contestRepo.save(one);
    }


    public void deleteContest(Integer id) {
        contestRepo.delete(id);
    }

    /**
     * 计算此次竞赛各队标准分
     * 时间复杂度：O(队伍数*题数)
     * @param contest Contest实体类
     * @return first各队标准分, second:各题每队得分
     */
    public Pair<double[], double[][]> calcContestScore(Contest contest, boolean[][] waClear) {
        Training training = getTrainingByContestId(contest.getId());
        ArrayList<TeamRanking> ranks = contest.getRanks();
        Integer pbCnt = contest.getPbCnt();
        double[] ans = new double[ranks.size()];
        double[][] preT = new double[pbCnt][];

        //超出wa限制判断
        for(int i = 0; i < ranks.size(); ++i) {
            waClear[i] = teatWaClear(ranks.get(i), -training.getWaCapcity());
        }

        for(int i = 0; i < pbCnt; ++i) {
            List<RawData> list = new ArrayList<>();
            for(int j = 0; j < ranks.size(); ++j) {
                PbStatus pbStatus = ranks.get(j).getPbStatus().get(i);
                list.add(new RawData((double) -pbStatus.calcPenalty(), pbStatus.isSolved() && !waClear[j][i]));
            }
            preT[i] = dataStder.std(list, training.getExpand(), training.getStandard());
            for(int j = 0; j < ranks.size(); ++j)
                ans[j] += (preT[i][j] <= 0) ? 0 : preT[i][j];
        }
        List<RawData> list = new ArrayList<>();
        for (double an : ans)
            list.add(new RawData(an, true));
        ans = dataStder.std(list, training.getExpand(), training.getStandard());
        return Pair.of(ans, preT);
    }

    private boolean[] teatWaClear(TeamRanking ranking, int capacity) {
        List<PbStatus> list = ranking.getPbStatus();
        boolean[] ans = new boolean[list.size()];
        TreeMap<PbStatus, Integer> ac = new TreeMap<>();
        int waCnt = 0;
        for (int i = 0; i < list.size(); ++i) {
            if(list.get(i).isSolved()) {
                waCnt += list.get(i).getWaCount();
                ac.put(list.get(i), i);
            }
        }
        while(ac.size() >= 2 && waCnt > ac.size() * capacity) {
            Map.Entry<PbStatus, Integer> entry = ac.lastEntry();
            ans[entry.getValue()] = true;
            waCnt -= entry.getKey().getWaCount();
            ac.remove(entry.getKey());
        }
        return ans;
    }


    public int[] calcRank(double[] score, Training training) {
        if(score == null) return null;
        AgnesClusterer clusterer = new AgnesClusterer(score);
        Map<Double, Integer> map = clusterer.clusterWithLimit(training.getMergeLimit(), true);
        int[] ans = new int[score.length];
        for(int i = 0; i < score.length; ++i) {
            ans[i] = map.get(score[i]);
        }
        return ans;
//        int[] rank = new int[score.length];
//        List<Pair<Double, Integer>> pairs = new ArrayList<>();
//        for(int i = 0; i < score.length; ++i)
//            pairs.add(Pair.of(score[i], i));
//        Collections.sort(pairs, (x, y) -> (y.getLeft().compareTo(x.getLeft())));
//        for(int i = 0; i < pairs.size(); ++i)
//            rank[pairs.get(i).getRight()] = i;
//        return rank;
    }

    public int[] calcRank(List<? extends Comparable> list) {
        if(list == null) return null;
        int[] rank = new int[list.size()];
        List<Pair<Comparable, Integer>> pairs = new ArrayList<>();
        for(int i = 0; i < list.size(); ++i)
            pairs.add(Pair.of(list.get(i), i));
        Collections.sort(pairs);
        for(int i = 0; i< pairs.size(); ++i)
            rank[pairs.get(i).getRight()] = i;
        return rank;
    }


    /// Assign


    public void saveAssign(AssignResult assign) {
        assignResultRepo.save(assign);
    }

    ////// vjudge

    public Contest parseVj(String contestName,
                           String contestType,
                           String stTime,
                           String edTime,
                           String myConfig,
                           String vjContest,
                           User addUser,
                           Integer stageId) throws IOException {
        Pair<String, String> rawDate = Pair.of(vjContest, myConfig);
        Contest contest = new Contest();
        contest.setRawData(rawDate);
        contest.setType(contestType);
        contest.setAddTime(LocalDateTime.now());
        contest.setName(contestName.trim());
        contest.setStartTime(LocalDateTime.parse(stTime));
        contest.setEndTime(LocalDateTime.parse(edTime));
        contest.setStage(getStageById(stageId));
        contest.setAddUid(addUser.getId());

        logger.info("导入比赛原始信息加载完毕：{}", contest);

        vjRankParser.parseRank(contest);

        logger.info("解析完毕：{}", contest);
        return contest;
    }
}
