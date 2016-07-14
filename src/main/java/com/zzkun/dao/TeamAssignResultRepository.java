package com.zzkun.dao;

import com.zzkun.model.TeamAssignResult;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/7/14.
 */
public interface TeamAssignResultRepository extends JpaRepository<TeamAssignResult, Integer> {
    @Override
    List<TeamAssignResult> findAll();

    @Override
    TeamAssignResult findOne(Integer integer);

    @Override
    long count();

    @Override
    void delete(Integer integer);

    @Override
    <S extends TeamAssignResult> S save(S entity);
}
