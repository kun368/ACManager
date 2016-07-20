package com.zzkun.dao;

import com.zzkun.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator on 2016/7/20.
 */
public interface TrainingRepo extends JpaRepository<Training, Integer> {
    @Override
    <S extends Training> S save(S s);

    @Override
    Training findOne(Integer integer);

    @Override
    List<Training> findAll();

    @Override
    void delete(Integer integer);
}
