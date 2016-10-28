package com.zzkun.service;

import com.zzkun.service.extoj.UVaService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kun on 2016/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class UVaServiceTest {

    @Autowired private UVaService uVaService;

//    @Test
//    public void getBookName() throws Exception {
//        List<String> bookName = uVaService.getBookName();
//        System.out.println(bookName);
//    }
//
//    @Test
//    public void getChapterName() throws Exception {
//        List<String> chapterName = uVaService.getChapterName();
//        System.out.println(chapterName);
//    }
//


}