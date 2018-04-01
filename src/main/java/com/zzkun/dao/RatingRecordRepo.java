package com.zzkun.dao;

import com.zzkun.model.RatingRecord;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

/**
 * Created by kun on 2016/8/13.
 */
public interface RatingRecordRepo extends JpaRepository<RatingRecord, Long>, JpaSpecificationExecutor<RatingRecord> {
    @Override
    List<RatingRecord> findAll();

    @Override
    <S extends RatingRecord> List<S> saveAll(Iterable<S> iterable);

    @Override
    void deleteInBatch(Iterable<RatingRecord> iterable);

    @Override
    void deleteAll(Iterable<? extends RatingRecord> entities);

    @Override
    <S extends RatingRecord> S save(S entity);

    @Override
    RatingRecord getOne(Long integer);

    @Override
    Optional<RatingRecord> findOne(Specification<RatingRecord> spec);

    @Override
    List<RatingRecord> findAll(Specification<RatingRecord> spec);

    @Override
    long count(Specification<RatingRecord> spec);
}
