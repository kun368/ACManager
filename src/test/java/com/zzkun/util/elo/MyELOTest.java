package com.zzkun.util.elo;

import jskills.Rating;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by kun on 2016/8/13.
 */
public class MyELOTest {

    private MyELO myELO = new MyELO();

    @Test
    public void calcPersonal() throws Exception {
        List<Pair<String, Integer>> list = new ArrayList<>();
        list.add(Pair.of("zzk", 1));
        list.add(Pair.of("heheda", 2));
        list.add(Pair.of("nihao", 3));
        Map<String, Rating> map = myELO.calcPersonal(new HashMap<>(), list);
        System.out.println(map);
    }
}