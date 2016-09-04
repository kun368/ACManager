package com.zzkun.dao;

import com.zzkun.model.UJoinT;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2016/7/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class UJoinTRepoTest {

    @Autowired private UJoinTRepo uJoinTRepo;


    @Test
    public void save() throws Exception {
//        List<Integer> list = Arrays.asList(92, 99, 103);
//        for (Integer integer : list) {
//            UJoinT cur = new UJoinT(integer, 4, UJoinT.Status.Pending);
//            uJoinTRepo.save(cur);
//        }
    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void findByUserIdAndTrainingId() throws Exception {
//        UJoinT res = uJoinTRepo.findByUserIdAndTrainingId(2, 1);
//        System.out.println(res);
//        res.setStatus(UJoinT.Status.Success);
//        uJoinTRepo.save(res);
//        System.out.println(res);
    }
}