package com.zzkun.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kun on 2016/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class AssignResultRepoTest {

    @Autowired
    private AssignResultRepo assignResultRepo;

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void count() throws Exception {
        System.out.println(assignResultRepo.count());
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

}