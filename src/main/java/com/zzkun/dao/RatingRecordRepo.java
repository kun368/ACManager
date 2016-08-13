package com.zzkun.dao;

import com.zzkun.model.RatingRecord;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by kun on 2016/8/13.
 */
public interface RatingRecordRepo
        extends JpaRepository<RatingRecord, Integer> {
    @Override
    List<RatingRecord> findAll();

    @Override
    <S extends RatingRecord> List<S> save(Iterable<S> iterable);

    @Override
    void deleteInBatch(Iterable<RatingRecord> iterable);

    @Override
    <S extends RatingRecord> S save(S entity);

    @Override
    RatingRecord findOne(Integer integer);

}
