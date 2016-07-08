package com.zzkun.dao;

import com.zzkun.model.UVaPbInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/7/7.
 */
public interface UVaPbInfoRepository extends JpaRepository<UVaPbInfo, Integer> {

    @Override
    <S extends UVaPbInfo> List<S> save(Iterable<S> iterable);

    @Override
    long count();

    @Override
    void deleteAllInBatch();

    @Override
    UVaPbInfo findOne(Integer integer);

    @Override
    List<UVaPbInfo> findAll();

    UVaPbInfo findByNum(Integer num);
}
