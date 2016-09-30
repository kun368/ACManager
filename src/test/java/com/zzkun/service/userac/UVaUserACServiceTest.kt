package com.zzkun.service.userac

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
class UVaUserACServiceTest {

    @Autowired
    lateinit var uVaUserACService: UVaUserACService

    @Autowired
    lateinit var userRepo: UserRepo

    @Test
    fun userACPbs() {
        val user = userRepo.findByUsername("kun368")
        val list = uVaUserACService.userACPbs(user)
        for(i in list) {
            println("${i.ojName}__${i.ojPbId}")
        }
        println(list.size)
    }
}