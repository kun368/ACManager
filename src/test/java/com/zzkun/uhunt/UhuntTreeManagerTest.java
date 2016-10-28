package com.zzkun.uhunt;

import com.zzkun.util.uhunt.UhuntTreeManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kun on 2016/7/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class UhuntTreeManagerTest {

    @Autowired
    private UhuntTreeManager uhuntTreeManager;

    @Test
    public void getChapterMap() throws Exception {
        System.out.println(uhuntTreeManager.getCptNodes());
    }

    @Test
    public void getBookMap() throws Exception {
        System.out.println(uhuntTreeManager.getBookNodes());
    }
}