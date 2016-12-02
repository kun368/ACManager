package com.zzkun;

import com.google.common.hash.Hashing;
import com.zzkun.dao.UserRepo;
import com.zzkun.model.User;
import com.zzkun.service.TrainingService;
import com.zzkun.util.uhunt.UhuntTreeManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by kun on 2016/7/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springmvc-servlet.xml")
public class MyTest {

    @Autowired private UhuntTreeManager uhuntTreeManager;

    @Autowired private TrainingService trainingService;

    @Autowired private UserRepo userRepo;


    @Test
    public void test1() {
//        Map<UHuntTreeNode, List<Integer>> bookMap = uhuntTreeManager.getBookNodes();
//
//        try(PrintWriter writer = new PrintWriter("result1.csv")) {
//            StringJoiner joiner1 = new StringJoiner(",");
//            joiner1.add("题号");
//            joiner1.add("标题");
//            joiner1.add("AC人数");
//            joiner1.add("AC次数");
//            joiner1.add("提交次数");
//            joiner1.add("AC次数/提交次数");
//            writer.println(joiner1);
//            Set<Integer> set = new HashSet<>();
//
//            for (List<Integer> list : bookMap.values()) {
//                for (Integer pb : list) {
//                    if(set.contains(pb))
//                        continue;
//                    set.add(pb);
//                    UVaPbInfo info = uVaPbInfoRepo.findByNum(pb);
//                    StringJoiner joiner = new StringJoiner(",");
//                    joiner.add("UVa" + pb);
//                    joiner.add(info.getTitle().replaceAll(",", ""));
//                    joiner.add(info.getDacu() + "");
//                    joiner.add(info.getAc() + "");
//                    joiner.add(info.allSubmitTimes() + "");
//                    joiner.add(String.format("%.3f", info.getAc() * 1.0 / info.allSubmitTimes()));
//                    writer.println(joiner);
//                }
//                writer.println("\n\n");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void test2() throws Exception {
//        Contest contest = trainingService.getContest(7);
//        Pair<double[], double[][]> pair = trainingService.calcTaamScore(contest);
//        double[] left = pair.getLeft();
//        System.out.println(Arrays.toString(left));
//
//        KMeansPlusPlusClusterer<DoublePoint> clusterer = new KMeansPlusPlusClusterer<DoublePoint>(20);
//
//        List<DoublePoint> list = new ArrayList<>();
//        for (double v : left) {
//            list.add(new DoublePoint(new double[]{v}));
//        }
//        List<? extends Cluster<DoublePoint>> res = clusterer.cluster(list);
//        for (Cluster<DoublePoint> re : res) {
//            System.out.println(re.getPoints());
//        }
    }

    /**
     * 修改所有数据库用户密码，sha1加密
     */
    @Test
    public void test3() throws Exception {
        List<User> all = userRepo.findAll();
        for (User user : all) {
            String ps = user.getPassword();
            ps = Hashing.sha1().hashString(ps, Charset.forName("utf8")).toString();
            user.setPassword(ps);
            userRepo.save(user);
        }
    }
}
