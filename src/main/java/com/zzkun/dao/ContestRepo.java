package com.zzkun.dao;

import com.zzkun.model.Contest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by kun on 2016/7/13.
 */
public interface ContestRepo extends JpaRepository<Contest, Integer>, JpaSpecificationExecutor<Contest> {
    @Override
    List<Contest> findAll();

    @Override
    Contest getOne(Integer integer);

    @Override
    <S extends Contest> S save(S entity);

    @Override
    void deleteById(Integer integer);

    @Override
    long count();

    @Override
    List<Contest> findAll(Specification<Contest> spec);
}
