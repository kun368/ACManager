package com.zzkun.service;

import com.zzkun.dao.AssignResultRepo;
import com.zzkun.model.AssignResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Created by Administrator on 2016/7/18.
 */
@Service
public class AssignService {

    @Autowired private AssignResultRepo assignResultRepo;

    public void saveAssign(AssignResult result) {
        assignResultRepo.save(result);
    }

}
