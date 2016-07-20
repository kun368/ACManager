package com.zzkun.service;

import com.zzkun.dao.ContestStageRepo;
import com.zzkun.dao.ContestRepo;
import com.zzkun.model.Contest;
import com.zzkun.model.ContestStage;
import com.zzkun.util.vjudge.VJRankParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2016/7/17.
 */
@Service
public class ContestService {

    @Autowired private ContestStageRepo contestStageRepo;

    @Autowired private ContestRepo contestRepo;

    @Autowired private VJRankParser vjRankParser;


    public List<ContestStage> getAllGroup() {
        return contestStageRepo.findAll();
    }

    public Contest parseVj(String contestName, String contestType,
                           Integer contestGroup, String vjContest) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("contestName", contestName);
            map.put("contestType", contestType);
            return vjRankParser.parse(Arrays.asList(vjContest.split("\n")), map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Contest saveContest(Contest contest) {
        return contestRepo.save(contest);
    }

    public Contest getContest(Integer id) {
        return contestRepo.findOne(id);
    }

    public List<Contest> findByStageId(Integer id) {
        return contestRepo.findByStageId(id);
    }
}
