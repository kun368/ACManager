package com.zzkun.dao;

import com.zzkun.model.UVaSubmit;
import com.zzkun.util.uhunt.UHuntWebGetter;
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
public class UVaSubmitRepoTest {

    @Autowired
    private UHuntWebGetter webGetter;

    @Autowired
    private UVaSubmitRepo uVaSubmitRepo;

    @Test
    public void save() throws Exception {
        List<UVaSubmit> list = webGetter.userACSubmits(617781);
        uVaSubmitRepo.save(list);
    }

    @Test
    public void findUVaSubmitByUvaId() throws Exception {
        List<UVaSubmit> res = uVaSubmitRepo.findByUvaId(617781);
        System.out.println(res);
    }
}