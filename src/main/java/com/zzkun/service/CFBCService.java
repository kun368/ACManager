package com.zzkun.service;

import com.zzkun.dao.BCUserInfoRepo;
import com.zzkun.dao.CFUserInfoRepo;
import com.zzkun.dao.UserRepo;
import com.zzkun.model.BCUserInfo;
import com.zzkun.model.CFUserInfo;
import com.zzkun.model.User;
import com.zzkun.util.web.BCWebGetter;
import com.zzkun.util.web.CFWebGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/8/5.
 */
@Service
public class CFBCService {
    private static final Logger logger = LoggerFactory.getLogger(CFBCService.class);

    @Autowired private CFWebGetter cfWebGetter;
    @Autowired private CFUserInfoRepo cfUserInfoRepo;
    @Autowired private BCWebGetter bcWebGetter;
    @Autowired private BCUserInfoRepo bcUserInfoRepo;
    @Autowired private UserRepo userRepo;

    public synchronized void flushCFUserInfo() {
        try {
            List<User> userList = userRepo.findAll();
            List<String> cfnameList = userList.stream()
                    .filter(x -> (!x.isAdmin() && StringUtils.hasText(x.getCfname())))
                    .map(User::getCfname)
                    .collect(Collectors.toList());
            logger.info("数据库所有CF用户：{}", cfnameList);
//        List<CFUserInfo> infoList = cfWebGetter.getUserInfos(cfnameList);
//        if(infoList == null)
            List<CFUserInfo> infoList = cfWebGetter.getUserInfos2(cfnameList);
            cfUserInfoRepo.save(infoList);
            logger.info("CF数据更新完毕！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, CFUserInfo> getCFUserInfoMap() {
        List<CFUserInfo> infoList = cfUserInfoRepo.findAll();
        return infoList.stream()
                .collect(Collectors.toMap(CFUserInfo::getCfname, x -> x));
    }

    public synchronized void flushBCUserInfo() {
        try {
            List<User> userList = userRepo.findAll();
            List<String> bcnameList = userList.stream()
                    .filter(x -> (!x.isAdmin() && StringUtils.hasText(x.getBcname())))
                    .map(User::getBcname)
                    .collect(Collectors.toList());
            logger.info("数据库所有BC用户：{}", bcnameList);
            List<BCUserInfo> infoList = bcWebGetter.getBCUserInfos(bcnameList);
            bcUserInfoRepo.save(infoList);
            logger.info("BC数据更新完毕！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, BCUserInfo> getBCUserInfoMap() {
        List<BCUserInfo> infoList = bcUserInfoRepo.findAll();
        return infoList.stream()
                .collect(Collectors.toMap(BCUserInfo::getBcname, x -> x));
    }
}
