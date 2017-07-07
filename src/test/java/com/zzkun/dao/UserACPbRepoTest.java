package com.zzkun.dao;

import com.zzkun.model.OJType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/2/26 0026.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springmvc-servlet.xml")
public class UserACPbRepoTest {

    @Autowired private UserACPbRepo repo;

    @Test
    public void countByOjNameAndOjPbId() throws Exception {
        for(int i = 1001; i <= 1200; ++i) {
            long l = repo.countByOjNameAndOjPbId(OJType.HDU, Integer.toString(i));
            System.out.println(i + " " + l);
        }
    }

}