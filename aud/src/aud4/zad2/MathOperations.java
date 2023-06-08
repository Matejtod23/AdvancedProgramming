package aud4.zad2;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class MathOperations {
    public static String statistics(List<? extends Number> numbers){
        DoubleSummaryStatistics doubleSummaryStatistics = numbers.stream().mapToDouble(i -> i.doubleValue()).summaryStatistics();
        double stdTmp = 0;
        for (Number n: numbers)
            stdTmp += Math.pow(n.doubleValue() - doubleSummaryStatistics.getAverage(), 2);
        double std = Math.sqrt(stdTmp / numbers.size());
        return String.format("Min: %f\nMax: %f\nCount: %f\nAverage: %f\nSum: %f\nStd: %f\n",
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getAverage(),
                doubleSummaryStatistics.getSum(),
                std);
    }

    public static void main(String[] args) {
        Random random = new Random();

        List<Integer> integers = new ArrayList<>();
        IntStream.range(0, 1000).forEach(i -> integers.add(random.nextInt(100) + 1));
        System.out.println(statistics(integers));

        List<Double> doubles = new ArrayList<>();
        IntStream.range(0, 1000).forEach(i -> doubles.add(random.nextDouble() * 100.0 + 1));
        System.out.println(statistics(doubles));
    }
}
