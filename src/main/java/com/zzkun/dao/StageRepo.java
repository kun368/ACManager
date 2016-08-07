package com.zzkun.dao;

import com.zzkun.model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/7/13.
         */
public interface StageRepo extends JpaRepository<Stage, Integer> {

    @Override
    List<Stage> findAll();

    @Override
    <S extends Stage> S save(S entity);

    @Override
    Stage findOne(Integer integer);

    List<Stage> findByTrainingId(Integer id);

    @Override
    long count();

    long countByTrainingId(Integer trainingId);
}
