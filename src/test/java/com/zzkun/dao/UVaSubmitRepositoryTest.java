package com.zzkun.dao;

import com.zzkun.model.UVaSubmit;
import com.zzkun.uhunt.UHuntWebGetter;
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
public class UVaSubmitRepositoryTest {

    @Autowired
    private UHuntWebGetter webGetter;

    @Autowired
    private UVaSubmitRepository uVaSubmitRepository;

    @Test
    public void save() throws Exception {
        List<UVaSubmit> list = webGetter.userACSubmits(617781);
        uVaSubmitRepository.save(list);
    }

    @Test
    public void findUVaSubmitByUvaId() throws Exception {
        List<UVaSubmit> res = uVaSubmitRepository.findByUvaId(617781);
        System.out.println(res);
    }

}