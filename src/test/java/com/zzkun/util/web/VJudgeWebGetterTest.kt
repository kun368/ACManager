package com.zzkun.util.web

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created by kun36 on 2016/12/28.
 */

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(locations = arrayOf("classpath:springmvc-servlet.xml"))
class VJudgeWebGetterTest {

    @Autowired lateinit var getter: VJudgeWebGetter


    @Test
    fun getUserACMap() {
        val map = getter.getUserACMap("kun368", "http://vjudge.net/user/%s")
        println(map)
    }

}