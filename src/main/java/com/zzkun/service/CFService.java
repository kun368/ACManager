package com.zzkun.service;

import com.zzkun.dao.CFUserInfoRepo;
import com.zzkun.dao.UserRepo;
import com.zzkun.model.CFUserInfo;
import com.zzkun.model.User;
import com.zzkun.util.cfapi.CFWebGetter;
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
public class CFService {
    private static final Logger logger = LoggerFactory.getLogger(CFService.class);

    @Autowired private CFWebGetter cfWebGetter;
    @Autowired private CFUserInfoRepo cfUserInfoRepo;
    @Autowired private UserRepo userRepo;

    public void flushCFUserInfo() {
        List<User> userList = userRepo.findAll();
        List<String> cfnameList = userList.stream()
                .filter(x -> (!x.isAdmin() && StringUtils.hasText(x.getCfname())))
                .map(User::getCfname)
                .collect(Collectors.toList());
        logger.info("数据库所有CF用户：{}", cfnameList);
        List<CFUserInfo> infoList = cfWebGetter.getUserInfos(cfnameList);
        cfUserInfoRepo.save(infoList);
        logger.info("CF数据更新完毕！");
    }

    public Map<String, CFUserInfo> getCFUserInfoMap() {
        List<CFUserInfo> infoList = cfUserInfoRepo.findAll();
        return infoList.stream()
                .collect(Collectors.toMap(CFUserInfo::getCfname, x -> x));
    }
}
