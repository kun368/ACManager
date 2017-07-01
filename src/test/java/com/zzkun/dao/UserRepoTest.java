package com.zzkun.dao;

import com.zzkun.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by kun on 2016/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:springmvc-servlet.xml")
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;


    @Test
    public void findAll() throws Exception {
        System.out.println(userRepo.findAll());
    }

    @Test
    public void count() throws Exception {
        System.out.println(userRepo.count());
    }

    @Test
    public void findOne() throws Exception {
        System.out.println(userRepo.findByUsername("abc"));
    }

    @Test
    public void save() throws Exception {
//        File file = new File(getClass().getClassLoader().getResource("uhunt/2016sum.csv").getFile());
//        List<String> lines = FileUtils.readLines(file, "utf8");
//        for (String line : lines) {
//            String[] split = line.split(",");
//            User user = new User(split[2], "123456", split[1], Integer.parseInt(split[0]), split[2], split[3], User.Type.New);
//            userRepo.save(user);
//        }
//        User user = new User("456", "456");
//        userRepo.save(user);
    }

//    @Test
//    public void deleteAllInBatch() throws Exception {
//        userRepo.deleteAllInBatch();
//    }

    @Test
    public void findByUvaId() throws Exception {
        User user = userRepo.findByUvaId(66666);
        System.out.println(user);
    }

    @Test
    public void findAll_pager() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<User> all = userRepo.findAll(pageRequest);
        System.out.println(all.getContent());
    }

    @Test
    public void findByRealName() throws Exception {
        User user = userRepo.findByRealName("张正锟");
        System.out.println(user);
    }

    @Test
    public void mytest() throws Exception {
        User user = userRepo.findByUsername("kun368");
        System.out.println(user);
        System.out.println(user.getuJoinTList());
        System.out.println(user.getAcPbList());
    }
}