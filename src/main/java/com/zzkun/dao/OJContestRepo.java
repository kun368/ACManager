package com.zzkun.dao;

import com.zzkun.model.OJContest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/8/7.
 */
public interface OJContestRepo extends JpaRepository<OJContest, Integer> {

    @Override
    List<OJContest> findAll();

    @Override
    <S extends OJContest> List<S> save(Iterable<S> iterable);

    @Override
    void deleteAllInBatch();
}
