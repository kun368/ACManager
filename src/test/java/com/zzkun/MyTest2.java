package com.zzkun;

import org.junit.Test;
import org.springframework.data.util.Pair;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
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
}
