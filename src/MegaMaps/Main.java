package MegaMaps;


import MegaMaps.Maps.ComparisonMap;
import MegaMaps.Maps.TreeMap;
import MegaMaps.Testing.BenchmarkSummary;
import MegaMaps.Testing.Benchmarks.AccessBenchmark;
import MegaMaps.Testing.Benchmarks.Benchmark;
import MegaMaps.Testing.Benchmarks.InsertionBenchmark;
import MegaMaps.Testing.Benchmarks.ReadWriteBenchmark;
import MegaMaps.Testing.Datasets.Dataset;
import MegaMaps.Testing.Datasets.RandomIntegerData;
import MegaMaps.Maps.HashMap;
import MegaMaps.Testing.Datasets.RandomStringData;
import MegaMaps.Testing.Datasets.UUIDData;
import MegaMaps.Testing.Tests.InsertReadTest;

import MegaMaps.Testing.Tests.KeySetTest;
import MegaMaps.Testing.Tests.SizeTest;
import MegaMaps.Testing.Tests.Test;
import MegaMaps.Utils.DesmosHelper;
import MegaMaps.Utils.Pair;

import java.util.UUID;


public class Main {

    public static void main(String[] args) throws Exception {
        //create a map
        Map<String,Integer> map = new HashMap<>();

        //Create a dataset
        Dataset<String,Integer> set = new RandomStringData(10000);

        //Run tests
        Test<String,Integer> test = new KeySetTest<>(set,map);
        System.out.println(test.run());
        test = new SizeTest<>(set,map);
        System.out.println(test.run());
        test = new InsertReadTest<>(set,map);
        System.out.println(test.run());


        //graph a benchmark

        DesmosHelper helper = new DesmosHelper(new DesmosHelper.plotFunction() {
            @Override
            public double getNextY(double x) {
                Map<Integer,Integer> map = new ComparisonMap<>();
                Dataset<Integer,Integer> set = new RandomIntegerData((int)x);
                Benchmark<Integer,Integer> benchmark = new ReadWriteBenchmark<>(set,map);
                BenchmarkSummary summary = new BenchmarkSummary(benchmark,5);
                //   System.out.println(x);
                return summary.getMean()/(double) x * 1000; //get time per insertion, multiply by amount to make numbers more reasonable
            }
        });
        System.out.println(helper.plot(10,100000,1000)); //get desmos values


        Map<Integer,Integer> map1 = new ComparisonMap<>();
        Dataset<Integer,Integer> set1 = new RandomIntegerData(500000);
        Benchmark<Integer,Integer> benchmark1 = new ReadWriteBenchmark<>(set1,map1);
        BenchmarkSummary summary = new BenchmarkSummary(benchmark1,10);
        Map<Integer,Integer> map2 = new HashMap<>(1000);
        Dataset<Integer,Integer> set2 = new RandomIntegerData(500000);
        Benchmark<Integer,Integer> benchmark2 = new ReadWriteBenchmark<>(set2,map2);
        BenchmarkSummary summary2 = new BenchmarkSummary(benchmark2,10);

        System.out.println(summary.comparison(summary2));

    }
}
