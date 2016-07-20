package com.zzkun.service;

import com.zzkun.dao.ContestStageRepo;
import com.zzkun.dao.TrainingRepo;
import com.zzkun.model.ContestStage;
import com.zzkun.model.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
@Service
public class TrainingService {

    @Autowired private TrainingRepo trainingRepo;

    @Autowired private ContestStageRepo contestStageRepo;

    public List<Training> allTraining() {
        return trainingRepo.findAll();
    }

    public Training getTraining(Integer id) {
        return trainingRepo.findOne(id);
    }

    public List<ContestStage> getAllStageByTrainingId(Integer id) {
        return contestStageRepo.findByTrainingId(id);
    }

}
