package com.zzkun.util.prob

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

/**
 * Created by Administrator on 2017/2/26 0026.
 */
@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(locations = arrayOf("classpath:springmvc-servlet.xml"))
class PbDiffCalcerTest() {

    @Autowired private lateinit var pbDiffCalcer: PbDiffCalcer

    @Test
    fun calcPbDiff() {

    }

    @Test
    fun allPbDiff() {
        val diff = pbDiffCalcer.allPbDiff()
        diff.forEach {
            print(it)
        }
    }

}