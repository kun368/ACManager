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
    CFUserInfo findOne(String integer);

    @Override
    <S extends CFUserInfo> List<S> save(Iterable<S> iterable);

    @Override
    long count();

    @Override
    void delete(String integer);
}
