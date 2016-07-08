package com.zzkun.dao;

import com.zzkun.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void count() throws Exception {
        System.out.println(userRepository.count());
    }

    @Test
    public void findOne() throws Exception {
        System.out.println(userRepository.findOne("abc"));
    }

    @Test
    public void save() throws Exception {
        userRepository.save(new User("jinrong", "123", 66666, "jinrong"));
    }
}