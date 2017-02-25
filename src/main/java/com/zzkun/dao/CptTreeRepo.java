package com.zzkun.dao;

import com.zzkun.model.CptTree;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2017/2/23 0023.
 */
public interface CptTreeRepo extends JpaRepository<CptTree, Integer> {

    @Override
    List<CptTree> findAll();

    @Override
    <S extends CptTree> S save(S s);

    @Override
    CptTree findOne(Integer integer);

    @Override
    long count();

    @Override
    void deleteAll();
}
