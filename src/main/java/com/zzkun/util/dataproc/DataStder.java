package com.zzkun.util.dataproc;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;

/**
 * 计算数据标准分
 * Created by kun on 2016/7/13.
 */
public class DataStder {

    /**
     * 计算数据标准分
     * 时间复杂度：O(N)
     *
     * @param list  原始数据list
     * @param alpha 放大倍数
     * @param beta  基准分
     * @return 每个数据的标准分
     */
    public static double[] std(List<RawData> list, double alpha, double beta) {
        double[] ans = new double[list.size()];
        //计算平均值和标准差
        Supplier<DoubleStream> stream =
                () -> list.stream().filter(RawData::isValid).mapToDouble(RawData::getData);
        int cnt = (int) stream.get().count();
        if (cnt == 0) return ans;
        if (cnt == 1) {
            for (int i = 0; i < list.size(); ++i)
                if (list.get(i).isValid())
                    ans[i] = beta;
            return ans;
        }
        double avg = stream.get().summaryStatistics().getAverage();
        double std = stream.get().map(x -> Math.pow(x - avg, 2)).sum();
        std = Math.sqrt(std / (cnt - 1));
        //计算标准分
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).isValid()) {
                ans[i] = std == 0 ? 0 : (list.get(i).getData() - avg) / std;
                ans[i] = alpha * ans[i] + beta;
            }
        }
        return ans;
    }
}
