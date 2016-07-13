package com.zzkun.dao;

import com.zzkun.model.ContestGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/7/13.
 */
public interface ContestGroupRepository extends JpaRepository<ContestGroup, Integer> {

    @Override
    List<ContestGroup> findAll();

    @Override
    <S extends ContestGroup> S save(S entity);

    @Override
    ContestGroup findOne(Integer integer);
}
