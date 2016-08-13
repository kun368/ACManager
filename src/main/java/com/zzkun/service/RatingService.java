package com.zzkun.service;

import com.zzkun.dao.RatingRecordRepo;
import com.zzkun.model.Contest;
import com.zzkun.model.RatingRecord;
import com.zzkun.model.Stage;
import com.zzkun.model.Training;
import com.zzkun.util.elo.MyELO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kun on 2016/8/13.
 */
@Service
public class RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);

    @Autowired private MyELO myELO;

    @Autowired private RatingRecordRepo ratingRecordRepo;


    public void flushTrainingUserRating(Training training) {
        List<Stage> stages = training.getStageList();
        List<Contest> contests = new ArrayList<>();
        for (Stage stage : stages) {
            contests.addAll(stage.getContestList());
        }
        //删除之前生成的纪录
        // TODO: 2016/8/13 待测试


    }
}
