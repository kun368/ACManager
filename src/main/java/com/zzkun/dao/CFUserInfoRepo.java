package com.zzkun.dao;

import com.zzkun.model.CFUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/8/5.
 */
public interface CFUserInfoRepo extends JpaRepository<CFUserInfo, String> {
    @Override
    List<CFUserInfo> findAll();

    @Override
    <S extends CFUserInfo> S save(S entity);

    @Override
    CFUserInfo getOne(String integer);

    @Override
    <S extends CFUserInfo> List<S> saveAll(Iterable<S> iterable);

    @Override
    long count();

    @Override
    void deleteById(String integer);
}
