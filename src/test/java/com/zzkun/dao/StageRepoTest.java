package com.zzkun.dao;

import com.zzkun.model.Stage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        Stage group1 = stageRepo.findOne(1);
        System.out.println(group1.getName());
    }

    @Test
    public void save() throws Exception {
        Stage group = new Stage("山东科技大学第一次ACM集训");
        stageRepo.save(group);
    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void findByTrainingId() throws Exception {
        System.out.println(stageRepo.findByTrainingId(1));
    }
}