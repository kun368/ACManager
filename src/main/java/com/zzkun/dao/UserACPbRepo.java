package com.zzkun.dao;

import com.zzkun.model.OJType;
import com.zzkun.model.User;
import com.zzkun.model.UserACPb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 16-9-11.
 */
public interface UserACPbRepo extends JpaRepository<UserACPb, Long> {

    @Override
    List<UserACPb> findAll();


//    List<UserACPb> findByUserAndOjName(User user, OJType ojName);

    @Override
    <S extends UserACPb> List<S> saveAll(Iterable<S> iterable);

    @Override
    <S extends UserACPb> S save(S s);

    @Override
    UserACPb getOne(Long aLong);

    @Override
    long count();

    @Override
    void deleteAll();

    List<UserACPb> findByUser(User user);

    long countByOjNameAndOjPbId(OJType ojName, String ojPbId);
}
