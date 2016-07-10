package com.zzkun.dao;

import com.zzkun.model.UVaPbInfo;
import com.zzkun.uhunt.UHuntWebGetter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class UVaPbInfoRepositoryTest {

    @Autowired
    private UHuntWebGetter uHuntWebGetter;

    @Autowired
    private UVaPbInfoRepository uVaPbInfoRepository;

    private long start;
    @Before
    public void start() {
        start = System.currentTimeMillis();
    }
    @After
    public void end() {
        System.out.println("Cost Time:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void save() throws Exception {
        List<UVaPbInfo> list = uHuntWebGetter.allPbInfo();
        uVaPbInfoRepository.save(list);
    }

    @Test
    public void findOne() throws Exception {
        UVaPbInfo one = uVaPbInfoRepository.findOne(37);
        System.out.println(one);
    }

    @Test
    public void count() throws Exception {
        System.out.println(uVaPbInfoRepository.count());
    }

    @Test
    public void findAll() throws Exception {
        List<UVaPbInfo> all = uVaPbInfoRepository.findAll();
        for (UVaPbInfo pb : all) {
            System.out.println(pb.getTitle());
            System.out.println("UVa" + pb.getNum() + ": " + pb.calcScore());
        }
    }

    @Test
    public void findByNum() {
        UVaPbInfo pbInfo = uVaPbInfoRepository.findByNum(100);
        System.out.println(pbInfo);
    }

    @Test
    public void deleteAllInBatch() throws Exception {
        uVaPbInfoRepository.deleteAllInBatch();

    }
}