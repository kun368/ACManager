package com.zzkun;

import com.zzkun.dao.UVaPbInfoRepository;
import com.zzkun.model.UHuntChapterTree;
import com.zzkun.model.UVaPbInfo;
import com.zzkun.uhunt.ChapterManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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

            for (List<Integer> list : bookMap.values()) {
                for (Integer pb : list) {
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
