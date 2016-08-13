package com.zzkun.dao;

import com.zzkun.model.Contest;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/7/13.
 */
public interface ContestRepo extends JpaRepository<Contest, Integer> {
    @Override
    List<Contest> findAll();

    @Override
    Contest findOne(Integer integer);

    @Override
    <S extends Contest> S save(S entity);

    @Override
    void delete(Integer integer);

    @Override
    long count();

    long countByStageId(Integer stageId);
}
