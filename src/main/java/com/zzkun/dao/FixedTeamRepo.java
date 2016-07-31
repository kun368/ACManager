package com.zzkun.dao;

import com.zzkun.model.FixedTeam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/7/30.
 */
public interface FixedTeamRepo extends JpaRepository<FixedTeam, Integer> {

    @Override
    List<FixedTeam> findAll();

    @Override
    <S extends FixedTeam> List<S> save(Iterable<S> iterable);

    @Override
    FixedTeam getOne(Integer integer);

    @Override
    long count();

    @Override
    void delete(Integer integer);
}
