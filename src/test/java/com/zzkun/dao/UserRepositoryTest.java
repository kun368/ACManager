package com.zzkun.dao;

import com.zzkun.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
        System.out.println(userRepository.findByUsername("abc"));
    }

    @Test
    public void save() throws Exception {
        userRepository.save(new User("kun768", "123456", "张正锟", 666666, "666666"));
    }

    @Test
    public void deleteAllInBatch() throws Exception {
        userRepository.deleteAllInBatch();
    }

    @Test
    public void findByUvaId() throws Exception {
        User user = userRepository.findByUvaId(66666);
        System.out.println(user);
    }

    @Test
    public void findAll_pager() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<User> all = userRepository.findAll(pageRequest);
        System.out.println(all.getContent());
    }

    @Test
    public void findByRealName() throws Exception {
        User user = userRepository.findByRealName("张正锟");
        System.out.println(user);
    }
}