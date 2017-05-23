package com.zzkun.service.extoj

import com.zzkun.dao.UserRepo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created by kun on 2016/9/29.
 */

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(locations = arrayOf("classpath:springmvc-servlet.xml"))
class VJudgeServiceTest {

    @Autowired
    lateinit var vjudgeService: VJudgeService

    @Autowired
    lateinit var userRepo: UserRepo

    @Test
    fun userACPbs() {
//        val user = userRepo.findByUsername("kun368")
//        val list = vjudgeService.getUserACPbsOnline(user)
//        for (acPb in list) {
//            println(acPb)
//        }
//        println(list.size)
    }

}