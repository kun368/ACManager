package com.zzkun.dao;

import com.zzkun.model.Training;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/7/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class TrainingRepoTest {

    @Autowired private TrainingRepo trainingRepo;

    @Test
    public void save() throws Exception {
        Training training = new Training();
        training.setStartDate(LocalDate.now());
        training.setEndDate(LocalDate.now());
        training.setName("第10次集训");
        training.setRemark("第10次集训开始啦~~~");
        trainingRepo.save(training);
    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void findAll() throws Exception {
        System.out.println(trainingRepo.findAll());
    }

    @Test
    public void delete() throws Exception {

    }

}