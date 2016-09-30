package com.zzkun.dao;

import com.zzkun.model.UserACPb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 16-9-11.
 */
public interface UserACPbRepo extends JpaRepository<UserACPb, Long> {

    @Override
    List<UserACPb> findAll();

    @Override
    <S extends UserACPb> List<S> save(Iterable<S> iterable);

    @Override
    <S extends UserACPb> S save(S s);

    @Override
    UserACPb findOne(Long aLong);

    @Override
    long count();
}
