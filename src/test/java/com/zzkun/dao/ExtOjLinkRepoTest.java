package com.zzkun.dao;

import com.zzkun.model.ExtOjLink;
import com.zzkun.model.OJType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by kun on 2016/10/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class ExtOjLinkRepoTest {

    @Autowired private ExtOjLinkRepo repo;

    @Test
    public void findAll() throws Exception {
        List<ExtOjLink> all = repo.findAll();
        System.out.println(all);
    }

    @Test
    public void findOne() throws Exception {
        ExtOjLink link = new ExtOjLink();
        link.setOj(OJType.HDU);
        link.setIndexLink("http://acm.hdu.edu.cn/");
        link.setPbStatusLink("http://acm.hdu.edu.cn/statistic.php?pid=%s");
        link.setProblemLink("http://acm.hdu.edu.cn/showproblem.php?pid=%s");
        link.setUserInfoLink("http://acm.hdu.edu.cn/userstatus.php?user=%s");
        repo.save(link);
    }

    @Test
    public void count() throws Exception {

    }

}