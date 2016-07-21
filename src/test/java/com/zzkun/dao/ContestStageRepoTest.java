package com.zzkun.dao;

import com.zzkun.model.ContestStage;
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
public class ContestStageRepoTest {

    @Autowired
    private ContestStageRepo contestStageRepo;

    @Test
    public void findAll() throws Exception {
        ContestStage group1 = contestStageRepo.findOne(1);
        System.out.println(group1.getName());
    }

    @Test
    public void save() throws Exception {
        ContestStage group = new ContestStage("山东科技大学第一次ACM集训");
        contestStageRepo.save(group);
    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void findByTrainingId() throws Exception {
        System.out.println(contestStageRepo.findByTrainingId(1));
    }
}