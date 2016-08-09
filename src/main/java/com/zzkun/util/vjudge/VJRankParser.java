package com.zzkun.util.vjudge;

import com.zzkun.dao.UserRepo;
import com.zzkun.model.Contest;
import com.zzkun.model.PbStatus;
import com.zzkun.model.TeamRanking;
import com.zzkun.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

/**
 * Vritual Judge榜单解析
 * Created by Administrator on 2016/6/27.
 */
@Component
public class VJRankParser {

    private static final Logger logger = LoggerFactory.getLogger(VJRankParser.class);

    @Autowired private UserRepo userRepo;

    private int parseTime(String str) {
        Pattern pattern = Pattern.compile("([\\d]+):([\\d]+):([\\d]+)");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()) {
            int t1 = parseInt(matcher.group(1));
            int t2 = parseInt(matcher.group(2));
            int t3 = parseInt(matcher.group(3));
            return t1 * 3600 + t2 * 60 + t3;
        }
        return 0;
    }

    private void parseSetTeamName(String str, TeamRanking team) {
        Pattern pattern = Pattern.compile("^(.*)\\((.*)\\)$");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()) {
            team.setAccount(matcher.group(1));
            team.setTeamName(matcher.group(2));
        } else {
            team.setAccount(str);
        }
    }

    private int parseWACnt(String str) {
        Pattern pattern = Pattern.compile("\\(-([\\d]+)\\)");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()) {
            return parseInt(matcher.group(1));
        }
        return 0;
    }

    private List<String> parseMember(String str) {
        String[] split = str.split(",");
        return new ArrayList<>(Arrays.asList(split));
    }

    public Contest parseRank(Contest contest) {

        String[] config = contest.getRawData().getRight().split("\n");
        Map<String, List<String>> account2team = new HashMap<>();
        for (String aConfig : config) {
            String[] names = aConfig.split("\\s+");
            if (names.length <= 1) continue;
            account2team.put(names[0],
                    new ArrayList<>(Arrays.asList(names).subList(1, names.length)));
        }
        logger.info("解析得到自定义账户对应表：{}", account2team);

        List<User> userList = userRepo.findAll();
        Set<String> vjnames = new HashSet<>();
        Set<String> realnames = new HashSet<>();
        userList.forEach(x -> {
            if(!x.isAdmin()) {
                if(x.getVjname() != null) vjnames.add(x.getVjname());
                if(x.getRealName() != null) realnames.add(x.getRealName());
            }
        });
        for (Map.Entry<String, List<String>> entry : account2team.entrySet()) {
            if(entry.getKey() != null) vjnames.add(entry.getKey());
            if(entry.getValue() != null && entry.getValue().size() == 1)
                realnames.add(entry.getValue().get(0));
        }
        logger.info("解析榜单所需要的所有合法用户：vjnames:{}, realnames:{}", vjnames, realnames);



        //读取比赛情况
        List<String> rankFile = Arrays.asList(contest.getRawData().getLeft().split("\n"));
        for (int i = 0; i < rankFile.size(); i++) {
            String[] split = rankFile.get(i).split("\t");
            if(i == 0) {
                contest.setPbCnt(split.length - 4);
                continue;
            }
            TeamRanking team = new TeamRanking(); //当前队伍
            parseSetTeamName(split[1].trim(), team);
            team.setSolvedCount(parseInt(split[2].trim()));

            if(Contest.TYPE_TEAM.equals(contest.getType()) ||
                    Contest.TYPE_MIX_TEAM.equals(contest.getType())) {
                if(!account2team.containsKey(team.getAccount()))
                    continue;
                team.setMember(account2team.get(team.getAccount()));
            }
            else if(Contest.TYPE_PERSONAL.equals(contest.getType())) {
                if(vjnames.contains(team.getAccount())) {
                    team.setMember(new ArrayList<>(Collections.singletonList(
                            userRepo.findByVjname(team.getAccount()).getRealName()
                    )));
                }
                else if(realnames.contains(team.getTeamName())) {
                    team.setMember(new ArrayList<>(Collections.singletonList(
                            team.getTeamName()
                    )));
                } else {
                    continue;
                }
            }

            for(int j = 0; j < contest.getPbCnt(); ++j) {
                List<PbStatus> pbStatus = team.getPbStatus();
                if(4 + j >= split.length) {
                    pbStatus.add(new PbStatus(false, 0, 0));
                }
                else {
                    int time = parseTime(split[4 + j]);
                    int wacnt = parseWACnt(split[4 + j]);
                    pbStatus.add(new PbStatus(time > 0, time, wacnt));
                }
            }
            contest.getRanks().add(team);
        }
        logger.info("榜单解析Rank结果：{}", contest.getRanks());
        return contest;
    }

}
