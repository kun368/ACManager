package com.zzkun.service;

import com.zzkun.dao.ContestGroupRepo;
import com.zzkun.dao.ContestRepo;
import com.zzkun.model.Contest;
import com.zzkun.model.ContestGroup;
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
    @Autowired
    private ContestGroupRepo contestGroupRepo;

    @Autowired
    private ContestRepo contestRepo;

    @Autowired
    private VJRankParser vjRankParser;


    public List<ContestGroup> getAllGroup() {
        return contestGroupRepo.findAll();
    }

    public Contest parseVj(String contestName, String contestType,
                           Integer contestGroup, String vjContest) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("contestName", contestName);
            map.put("contestType", contestType);
            Contest contest = vjRankParser.parse(Arrays.asList(vjContest.split("\n")), map);
            contest.setContestGroup(contestGroupRepo.findOne(contestGroup));
            return contest;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Contest saveContest(Contest contest) {
        return contestRepo.save(contest);
    }
}
