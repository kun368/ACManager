package com.zzkun.dao;

import com.zzkun.model.UJoinT;
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

    UJoinT findByUserIdAndTrainingId(Integer userId, Integer trainingId);

    List<UJoinT> findByUserId(Integer userId);

    List<UJoinT> findByTrainingId(Integer trainingId);
}
