package com.zzkun;

import com.zzkun.dao.UVaPbInfoRepository;
import com.zzkun.model.UHuntChapterTree;
import com.zzkun.model.UVaPbInfo;
import com.zzkun.util.uhunt.ChapterManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by kun on 2016/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class MyTest {

    @Autowired
    private ChapterManager chapterManager;

    @Autowired
    private UVaPbInfoRepository uVaPbInfoRepository;

    private long start;

    @Before
    public void start() {
        start = System.currentTimeMillis();
    }

    @After
    public void end() {
        System.out.println(System.currentTimeMillis() - start);
    }

    @Test
    public void test1() {
        Map<UHuntChapterTree, List<Integer>> bookMap = chapterManager.getBookMap();

        try(PrintWriter writer = new PrintWriter("result1.csv")) {
            StringJoiner joiner1 = new StringJoiner(",");
            joiner1.add("题号");
            joiner1.add("标题");
            joiner1.add("AC人数");
            joiner1.add("AC次数");
            joiner1.add("提交次数");
            joiner1.add("AC次数/提交次数");
            writer.println(joiner1);
            Set<Integer> set = new HashSet<>();

            for (List<Integer> list : bookMap.values()) {
                for (Integer pb : list) {
                    if(set.contains(pb))
                        continue;
                    set.add(pb);
                    UVaPbInfo info = uVaPbInfoRepository.findByNum(pb);
                    StringJoiner joiner = new StringJoiner(",");
                    joiner.add("UVa" + pb);
                    joiner.add(info.getTitle().replaceAll(",", ""));
                    joiner.add(info.getDacu() + "");
                    joiner.add(info.getAc() + "");
                    joiner.add(info.allSubmitTimes() + "");
                    joiner.add(String.format("%.3f", info.getAc() * 1.0 / info.allSubmitTimes()));
                    writer.println(joiner);
                }
                writer.println("\n\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
