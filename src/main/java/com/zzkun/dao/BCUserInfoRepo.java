package com.zzkun.dao;

import com.zzkun.model.BCUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/8/10.
 */
public interface BCUserInfoRepo extends JpaRepository<BCUserInfo, String> {
    @Override
    List<BCUserInfo> findAll();

    @Override
    <S extends BCUserInfo> List<S> save(Iterable<S> iterable);

    @Override
    <S extends BCUserInfo> S save(S s);

    @Override
    BCUserInfo findOne(String s);

    @Override
    void delete(String s);
}
