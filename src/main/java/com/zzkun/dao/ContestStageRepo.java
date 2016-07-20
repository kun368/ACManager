package com.zzkun.dao;

import com.zzkun.model.ContestStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/7/13.
 */
public interface ContestStageRepo extends JpaRepository<ContestStage, Integer> {

    @Override
    List<ContestStage> findAll();

    @Override
    <S extends ContestStage> S save(S entity);

    @Override
    ContestStage findOne(Integer integer);

    List<ContestStage> findByTrainingId(Integer id);
}
