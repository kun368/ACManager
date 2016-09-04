package com.zzkun.dao;

import com.zzkun.model.UVaSubmit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/7/7.
 */
public interface UVaSubmitRepo extends JpaRepository<UVaSubmit, Long> {

    @Override
    <S extends UVaSubmit> List<S> save(Iterable<S> iterable);

    List<UVaSubmit> findByUvaId(Integer uvaid);

    @Override
    long count();
}
