package com.zzkun.dao;

import com.zzkun.model.Training;
import com.zzkun.model.UJoinT;
import com.zzkun.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/7/21.
 */
public interface UJoinTRepo extends JpaRepository<UJoinT, Integer> {

    @Override
    <S extends UJoinT> S save(S s);

    @Override
    UJoinT findOne(Integer integer);

    UJoinT findByUserAndTraining(User user, Training training);
}
