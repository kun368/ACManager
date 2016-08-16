package com.zzkun;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.math3.ml.clustering.*;
import org.junit.Test;
import org.springframework.data.util.Pair;

import java.math.BigInteger;
import java.net.URI;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Administrator on 2016/7/23.
 */
public class MyTest2 {
    @Test
    public void test2() throws Exception {
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        Vector<Future<List<String>>> vector = new Vector<>();
        for(int i = 0; i < 20; ++i) {
            int finalI = i;
            vector.add(threadPool.submit(() -> {
                List<String> list = new ArrayList<>();
                System.out.println(System.nanoTime() + "-----:" + finalI);
                for(int j = 0; j < 10000; ++j)
                    list.add(j + "by" + finalI);
                return list;
            }));
        }
        threadPool.shutdown();
        List<String> list = new ArrayList<>();
        for (Future<List<String>> future : vector) {
            System.out.println(future.isDone() + " " + future.isCancelled());
//            list.addAll(future.get());
        }
        System.out.println(list.size());
//        System.out.println(list);
    }

    @Test
    public void test3() throws Exception {
        List<Integer> list = new ArrayList<>();
        list.addAll(null);
        System.out.println(list);
    }

    @Test
    public void test4() throws Exception {
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for(int i = 0; i < 10; ++i) {
            list.add(i+"");
        }
        for(int i = 0; i < 10; ++i) {
            list1.add(i+"");
        }
        System.out.println(list.equals(list1));
    }

    @Test
    public void test5() throws Exception {
        Pair<String, double[]> pair = Pair.of("abc", new double[]{1, 2, 3, 4});
        System.out.println(pair);
    }

    @Test
    public void test6() throws Exception {
        Clusterer<DoublePoint> clusterer = new KMeansPlusPlusClusterer<DoublePoint>(3);
        List<DoublePoint> list = new ArrayList<>();

        list.add(new DoublePoint(new double[]{1}));
        list.add(new DoublePoint(new double[]{1.5}));
        list.add(new DoublePoint(new double[]{1.8}));
        list.add(new DoublePoint(new double[]{3.5}));
        list.add(new DoublePoint(new double[]{3.6}));
        list.add(new DoublePoint(new double[]{4}));
        list.add(new DoublePoint(new double[]{4.2}));
        System.out.println(list);

        List<? extends Cluster<DoublePoint>> res = clusterer.cluster(list);
        System.out.println("!!!");
        System.out.println(res.size());
        for (Cluster<DoublePoint> re : res) {
            System.out.println(re.getPoints());
        }
    }

    @Test
    public void test7() throws Exception {
        String str = IOUtils.toString(new URI("http://contests.acmicpc.info/contests.json"), "utf8");
        JSONArray array = JSON.parseArray(str);
        for(int i = 0; i < array.size(); ++i) {
            JSONObject object = array.getJSONObject(i);
            System.out.println(object);
        }
    }

    @Test
    public void test8() throws Exception {
        String s = IOUtils.toString(new URI("http://acm.hust.edu.cn/rank/data/contest_standing/125874.json"), "utf8");
        System.out.println(s);
    }


    public void test9(boolean[] x) throws Exception {
        x = new boolean[10];
    }

    @Test
    public void test10() throws Exception {
        boolean[] x = new boolean[100];
        test9(x);
        System.out.println(x.length);

    }
}
