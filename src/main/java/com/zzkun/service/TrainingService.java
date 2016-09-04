package com.zzkun.service;

import com.zzkun.dao.*;
import com.zzkun.model.*;
import com.zzkun.util.date.MyDateFormater;
import com.zzkun.util.rank.VJRankParser;
import com.zzkun.util.stder.DataStder;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.Collator;
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
        List<User> result = userRepo.findAll(uids);
        Collator instance = Collator.getInstance(Locale.CHINA);
        Collections.sort(result, (a, b) -> instance.compare(a.getRealName(), b.getRealName()));
        return result;
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
                               Double mergeLimit, Integer waCapcity,
                               Double ratingBase, Double ratingMultiple) {
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
        training.setRatingBase(ratingBase);
        training.setRatingMultiple(ratingMultiple);
        logger.info("修改后的Training：{}", training);
        trainingRepo.save(training);
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

    //// Stage

    public Stage getStageById(Integer id) {
        return stageRepo.findOne(id);
    }

    public void addStage(Stage stage) {
        stageRepo.save(stage);
    }

    public void modifyStage(Integer id, String name, String beginTime, String endTime, String remark, Boolean countToRating) {
        Stage stage = getStageById(id);
        if(StringUtils.hasText(name))
            stage.setName(name);
        if(StringUtils.hasText(remark))
            stage.setRemark(remark);
        stage.setStartDate(LocalDate.parse(beginTime));
        stage.setEndDate(LocalDate.parse(endTime));
        stage.setCountToRating(countToRating);
        stageRepo.save(stage);
    }

    public Map<Integer, Integer> getstageSizeMap(List<Stage> stageList) {
        Map<Integer, Integer> map = new HashMap<>(stageList.size());
        for (Stage stage : stageList) {
            map.put(stage.getId(), stage.getContestList().size());
        }
        return map;
    }

    //// fixed Team

    public void addOrModifyFixedTeam(Integer trainingId, FixedTeam fixedTeam) {
        Training training = getTrainingById(trainingId);
        if(fixedTeam.getId() == -1) {
            fixedTeam.setId(null);
        }
        fixedTeam.setTraining(training);
        fixedTeamRepo.save(fixedTeam);
    }

    public void deleteFixedTeam(Integer fixedTeamId) {
        fixedTeamRepo.delete(fixedTeamId);
    }

    //// Contest

    public Contest saveContest(Contest contest) {
        return contestRepo.save(contest);
    }

    public Contest getContest(Integer id) {
        return contestRepo.findOne(id);
    }

    public List<Contest> getContestByStageId(Integer id) {
        return stageRepo.findOne(id).getContestList();
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


    /// Assign

    public void saveAssign(AssignResult assign) {
        assignResultRepo.save(assign);
    }

    ////// rank

    public Contest parseVj(String contestName,
                           String contestType,
                           String stTime, String edTime,
                           String source, String sourceDetail, String sourceUrl,
                           String myConfig, String vjContest,
                           Boolean realContest,
                           User addUser,
                           Integer stageId) throws IOException {
        Pair<String, String> rawDate = Pair.of(vjContest, myConfig);
        Contest contest = new Contest();
        contest.setRawData(rawDate);
        contest.setType(contestType);
        contest.setAddTime(LocalDateTime.now());
        contest.setName(contestName.trim());
        contest.setStartTime(MyDateFormater.toDT1(stTime));
        contest.setEndTime(MyDateFormater.toDT1(edTime));
        contest.setSource(source.trim());
        contest.setSourceDetail(sourceDetail.trim());
        contest.setSourceUrl(sourceUrl.trim());
        contest.setRealContest(realContest);
        contest.setStage(getStageById(stageId));
        contest.setAddUid(addUser.getId());

        logger.info("导入比赛原始信息加载完毕：{}", contest);

        vjRankParser.parseRank(contest);

        logger.info("解析完毕：{}", contest);
        return contest;
    }
}
