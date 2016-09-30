package com.zzkun.service.userac

import com.zzkun.dao.UserRepo
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created by kun on 2016/9/30.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(locations = arrayOf("classpath:springmvc-servlet.xml"))
class UserACServiceTest {

    @Autowired
    lateinit var userACService: UserACService

    @Autowired
    lateinit var userRepo: UserRepo

    @Test
    fun userAllAC() {
        val user = userRepo.findByUsername("kun368")
        val list = userACService.userAllAC(user)
        for(i in list) {
            println(i)
        }
        println(list.size)
    }

    @Test
    fun flushACDB() {
        userACService.flushACDB()
    }
}