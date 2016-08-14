package com.zzkun.dao;

import com.zzkun.model.Contest;
import com.zzkun.util.vjudge.VJRankParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.util.Arrays;
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
//        List<Contest> all = contestRepo.findAll();
//        for (Contest contest : all) {
//            System.out.println(contest);
//            System.out.println(contest.getRanks());
//        }
//        Contest contest = new Contest();
//        contest.setAddUid(1);
//        List<Contest> all = contestRepo.findAll(Example.of(contest));
//        System.out.println(all);
        List<Contest> list = contestRepo.findAll((root, query, cb) -> {
            Predicate p1 = cb.equal(root.get("addUid").as(Integer.class), 1);
            return query.where(p1).getRestriction();
        });
        System.out.println(list);
    }

    @Test
    public void findOne() throws Exception {
//        Contest one = contestRepo.findOne(1);
//        System.out.println(one);
//        System.out.println(one.getRanks());
//        Pair<double[], double[][]> pair = one.calcTemesStdScore(-20, 100);
//        System.out.println(Arrays.toString(pair.getLeft()));
//        System.out.println(Arrays.toString(pair.getRight()));
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
//            double[] doubles = contest.calcContestScore(20, 100);
//            assertEquals(doubles.length, contest.getRanks().size());
//        }
    }
}