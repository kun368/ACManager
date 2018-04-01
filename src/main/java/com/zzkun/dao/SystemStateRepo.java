package com.zzkun.dao;

import com.zzkun.model.SystemState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Administrator on 2017/2/21 0021.
 */
public interface SystemStateRepo extends JpaRepository<SystemState, LocalDate> {

    @Override
    List<SystemState> findAll();

    @Override
    <S extends SystemState> S save(S s);

    @Override
    SystemState getOne(LocalDate localDate);

    @Override
    long count();

    @Override
    void deleteById(LocalDate localDate);
}
