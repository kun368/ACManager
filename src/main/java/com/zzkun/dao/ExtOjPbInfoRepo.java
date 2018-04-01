package com.zzkun.dao;

import com.zzkun.model.ExtOjPbInfo;
import com.zzkun.model.OJType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/10/15.
 */
public interface ExtOjPbInfoRepo extends JpaRepository<ExtOjPbInfo, Long> {
    @Override
    List<ExtOjPbInfo> findAll();

    @Override
    <S extends ExtOjPbInfo> List<S> saveAll(Iterable<S> iterable);

    @Override
    <S extends ExtOjPbInfo> S save(S s);

    @Override
    ExtOjPbInfo getOne(Long aLong);

    ExtOjPbInfo findByOjNameAndPid(OJType ojName, String pid);

    ExtOjPbInfo findByOjNameAndNum(OJType ojName, String num);

    List<ExtOjPbInfo> findByOjName(OJType ojName);

    @Override
    void deleteAll();
}
