package com.zzkun.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class UVaServiceTest {

    @Autowired
    private UVaService uVaService;

    @Test
    public void getBookName() throws Exception {
        List<String> bookName = uVaService.getBookName();
        System.out.println(bookName);
    }

    @Test
    public void getChapterName() throws Exception {
        List<String> chapterName = uVaService.getChapterName();
        System.out.println(chapterName);
    }

    @Test(timeout = 10000)
    public void getCptCnt() throws Exception {
        List<Integer> users = new ArrayList<>();
        users.add(617781);
        List<List<Integer>> cptCnt = uVaService.getCptCnt(users);
        System.out.println(cptCnt);
    }

    @Test
    public void getBookCnt() throws Exception {
        List<Integer> users = new ArrayList<>();
        users.add(617781);
        List<List<Integer>> cptCnt = uVaService.getBookCnt(users);
        System.out.println(cptCnt);
    }

    @Test
    public void flushUVaSubmit() throws Exception {
        uVaService.flushUVaSubmit();
    }
}