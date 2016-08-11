package com.zzkun.dao;

import com.zzkun.model.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by kun on 2016/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class StageRepoTest {

    @Autowired
    private StageRepo stageRepo;

    @Test
    public void findAll() throws Exception {
        List<Stage> all = stageRepo.findAll();
        System.out.println(all);
    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void findByTrainingId() throws Exception {
//        System.out.println(stageRepo.findByTrainingId(1));
    }
}