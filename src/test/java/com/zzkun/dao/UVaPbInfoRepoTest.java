package com.zzkun.dao;

import com.zzkun.model.UVaPbInfo;
import com.zzkun.util.uhunt.UHuntWebGetter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by kun on 2016/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class UVaPbInfoRepoTest {

    @Autowired
    private UHuntWebGetter uHuntWebGetter;

    @Autowired
    private UVaPbInfoRepo uVaPbInfoRepo;

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
        uVaPbInfoRepo.save(list);
    }

    @Test
    public void findOne() throws Exception {
        UVaPbInfo one = uVaPbInfoRepo.findOne(37);
        System.out.println(one);
    }

    @Test
    public void count() throws Exception {
        System.out.println(uVaPbInfoRepo.count());
    }

    @Test
    public void findAll() throws Exception {
        List<UVaPbInfo> all = uVaPbInfoRepo.findAll();
        for (UVaPbInfo pb : all) {
            System.out.println(pb.getTitle());
            System.out.println("UVa" + pb.getNum() + ": " + pb.calcScore());
        }
    }

    @Test
    public void findByNum() {
        UVaPbInfo pbInfo = uVaPbInfoRepo.findByNum(100);
        System.out.println(pbInfo);
    }

    @Test
    public void deleteAllInBatch() throws Exception {
        uVaPbInfoRepo.deleteAllInBatch();

    }
}