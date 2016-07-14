package com.zzkun.util.vjudge;

import com.zzkun.model.Contest;
import com.zzkun.model.PbStatus;
import com.zzkun.model.TeamRanking;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
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

    public Contest parse(List<String> rankFile, Map<String, String> config) throws IOException {
        //读取比赛配置文件，加载配置
        Contest status = new Contest();
        status.setName(config.getOrDefault("contestName", ""));
        status.setTime(LocalDate.parse(config.getOrDefault("contestTime", LocalDate.now().toString())));
        status.setType(config.getOrDefault("contestType", Contest.TYPE_TEAM));
        //读取比赛情况
        int lineCnt = 0;
        String line;
        for (int i = 0; i < rankFile.size(); i++) {
            String[] split = rankFile.get(i).split("\t");
            if(i == 0) {
                status.setPbCnt(split.length - 4);
                continue;
            }
            TeamRanking team = new TeamRanking(); //当前队伍
            parseSetTeamName(split[1].trim(), team);
            team.setSolvedCount(parseInt(split[2].trim()));
            //assign.setMember();
            for(int j = 0; j < status.getPbCnt(); ++j) {
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
            status.getRanks().add(team);
        }
        return status;
    }
}
