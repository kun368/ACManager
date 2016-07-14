package com.zzkun.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class TeamAssignResultRepositoryTest {

    @Autowired
    private TeamAssignResultRepository teamAssignResultRepository;

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void count() throws Exception {
        System.out.println(teamAssignResultRepository.count());
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

}