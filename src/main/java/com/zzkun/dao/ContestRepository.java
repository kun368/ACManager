package com.zzkun.dao;

import com.zzkun.model.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/7/13.
 */
public interface ContestRepository extends JpaRepository<Contest, Integer> {
    @Override
    List<Contest> findAll();

    @Override
    Contest findOne(Integer integer);

    @Override
    <S extends Contest> S save(S entity);
}
