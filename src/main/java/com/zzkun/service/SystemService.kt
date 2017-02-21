package com.zzkun.service

import com.zzkun.dao.ContestRepo
import com.zzkun.dao.SystemStateRepo
import com.zzkun.dao.UserACPbRepo
import com.zzkun.dao.UserRepo
import com.zzkun.model.SystemState
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

/**
 * Created by Administrator on 2017/2/21 0021.
 */
@Service
class SystemService(
        @Autowired private val systemStateRepo: SystemStateRepo,
        @Autowired private val userRepo: UserRepo,
        @Autowired private val contestRepo: ContestRepo,
        @Autowired private val userACPbRepo: UserACPbRepo) {

    companion object {
        private val logger = LoggerFactory.getLogger(SystemService::class.java)
    }

    private fun curState(): SystemState {
        return SystemState(
                LocalDate.now(),
                userRepo.count(),
                userACPbRepo.count(),
                contestRepo.count()
        )
    }

    fun saveCurState() {
        val cur = curState()
        systemStateRepo.save(cur)
        logger.info("保存当前系统状态${cur}")
    }

    fun stateHistory(): List<SystemState> {
        return systemStateRepo
                .findAll()
                .sortedBy { it.date }
    }
}
