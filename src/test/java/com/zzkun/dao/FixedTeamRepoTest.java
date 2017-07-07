package com.zzkun.dao;

import com.zzkun.model.FixedTeam;
import com.zzkun.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/8/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springmvc-servlet.xml")
public class FixedTeamRepoTest {

    @Autowired private FixedTeamRepo fixedTeamRepo;
    @Autowired private TrainingRepo trainingRepo;
    @Autowired private UserRepo userRepo;

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void findOne() throws Exception {
    }

    @Test
    public void count() throws Exception {
    }

    @Test
    public void delete() throws Exception {

    }

}