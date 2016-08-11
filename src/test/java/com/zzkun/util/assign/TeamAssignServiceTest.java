package com.zzkun.util.assign;

import com.zzkun.dao.UserRepo;
import com.zzkun.model.AssignResult;
import com.zzkun.model.User;
import com.zzkun.service.TeamAssignService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class TeamAssignServiceTest {

    @Autowired private UserRepo userRepo;

    @Autowired private TeamAssignService teamAssignService;

    @Test
    public void assign() throws Exception {
        List<User> all = userRepo.findAll();
        List<Integer> users = new ArrayList<>();
        for (User user : all)
            users.add(user.getId());
        teamAssignService.assign(users, 5, AssignResult.Type.NoRepeat);
    }

}