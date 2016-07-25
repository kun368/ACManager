package com.zzkun.dao;

import com.zzkun.model.Contest;
import com.zzkun.util.vjudge.VJRankParser;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/7/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class ContestRepoTest {

    @Autowired
    private ContestRepo contestRepo;

    @Autowired
    private VJRankParser vjRankParser;


    @Test
    public void findAll() throws Exception {
        List<Contest> all = contestRepo.findAll();
        for (Contest contest : all) {
            System.out.println(contest);
            System.out.println(contest.getRanks());
        }
    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void save() throws Exception {
//        List<String> list = FileUtils.readLines(new File("temp/vjudge"), "utf8");
//        Contest contest = vjRankParser.parseRank(list, new HashMap<>());
//        contestRepo.save(contest);
    }

    @Test(timeout = 10000)
    public void calcTemesStdScore() throws Exception {
//        List<String> list = FileUtils.readLines(new File("temp/vjudge"), "utf8");
//        for(int i = 0; i < 1000; ++i) {
//            Contest contest = vjRankParser.parseRank(list, new HashMap<>());
//            double[] doubles = contest.calcTemesStdScore(20, 100);
//            assertEquals(doubles.length, contest.getRanks().size());
//        }
    }
}