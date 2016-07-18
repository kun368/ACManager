package com.zzkun.util.assign;

import com.zzkun.model.AssignResult;
import com.zzkun.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.zzkun.model.AssignResult.*;

/**
 * 随机分队工具
 * Created by kun on 2016/7/14.
 */
@Component
public class TeamAssignUtil {


    /**
     * 随机分队
     * @param users 要被分队的用户列表
     * @return 分队结果
     */
    public AssignResult assign(List<Integer> users, Type type) {
        if(type.equals(Type.RANDOM))
            return randomAssign(users);
        return null;
    }

    /**
     * 真正随机分队，3人一队
     * 时间复杂度：O(人数)
     * Type: RANDOM
     */
    private AssignResult randomAssign(List<Integer> users) {
        AssignResult result = new AssignResult();
        result.setType(Type.RANDOM);

        Collections.shuffle(users);
        for(int i = 0; i < users.size(); i += 3) {
            List<Integer> team = new ArrayList<>();
            for(int j = i; j < users.size() && j < i+3; ++j)
                team.add(users.get(j));
            result.getTeamList().add(team);
        }
        return result;
    }
}
