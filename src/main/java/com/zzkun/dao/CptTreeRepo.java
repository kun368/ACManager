package com.zzkun.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23 0023.
 */
public interface CptTreeRepo extends JpaRepository<CptTreeRepo, Integer> {

    @Override
    List<CptTreeRepo> findAll();

    @Override
    <S extends CptTreeRepo> S save(S s);

    @Override
    CptTreeRepo findOne(Integer integer);

    @Override
    long count();

    @Override
    void deleteAll();
}
