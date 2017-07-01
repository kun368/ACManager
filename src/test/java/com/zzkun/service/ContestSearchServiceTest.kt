package com.zzkun.service

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created by kun36 on 2016/12/29.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(locations = arrayOf("classpath*:springmvc-servlet.xml"))
class ContestSearchServiceTest {

    @Autowired lateinit var serv : ContestSearchService

    @Test
    fun splitParms() {

    }

    @Test
    fun find() {
        val list = serv.find("大学生")
        println(list)
    }

}