package com.zzkun.dao;

import com.zzkun.model.AssignResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/7/14.
 */
public interface AssignResultRepo extends JpaRepository<AssignResult, Integer> {
    @Override
    List<AssignResult> findAll();

    @Override
    AssignResult findOne(Integer integer);

    @Override
    long count();

    @Override
    void delete(Integer integer);

    @Override
    <S extends AssignResult> S save(S entity);

    @Override
    Page<AssignResult> findAll(Pageable pageable);

    List<AssignResult> findByTrainingId(Integer trainingId);
}
