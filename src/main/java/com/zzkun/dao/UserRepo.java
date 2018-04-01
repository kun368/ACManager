package com.zzkun.dao;

import com.zzkun.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/7/6.
 */
public interface UserRepo extends JpaRepository<User, Integer> {

    @Override
    List<User> findAll();

    @Override
    User getOne(Integer integer);

    @Override
    long count();

    @Override
    <S extends User> S save(S entity);

    @Override
    <S extends User> List<S> saveAll(Iterable<S> iterable);

    User findByUsername(String username);

    User findByVjname(String vjname);

    User findByBcname(String bcname);

    User findByUvaId(Integer uvaid);

    User findByRealName(String realName);

    @Override
    List<User> findAllById(Iterable<Integer> iterable);
}
