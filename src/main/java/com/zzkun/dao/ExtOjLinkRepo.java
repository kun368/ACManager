package com.zzkun.dao;

import com.zzkun.model.ExtOjLink;
import com.zzkun.model.OJType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by kun on 2016/10/31.
 */
public interface ExtOjLinkRepo extends JpaRepository<ExtOjLink, OJType> {

    @Override
    List<ExtOjLink> findAll();

    @Override
    ExtOjLink findOne(OJType ojType);

    @Override
    long count();

    @Override
    <S extends ExtOjLink> S save(S s);
}
