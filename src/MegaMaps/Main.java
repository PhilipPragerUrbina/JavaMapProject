package MegaMaps;


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
import MegaMaps.Testing.Tests.Test;
import MegaMaps.Utils.DesmosHelper;
import MegaMaps.Utils.Pair;

import java.util.UUID;


public class Main {

    public static void main(String[] args) throws Exception {
        HashMap<UUID,Integer> map = new HashMap<>(1000);
        Dataset<UUID,Integer> set = new UUIDData(10000);
        Test<UUID,Integer> test = new InsertReadTest<>(set,map);
        System.out.println(test.run());
        for (Pair pair: map) {
         //   System.out.println(pair);
        }


        DesmosHelper helper = new DesmosHelper(new DesmosHelper.plotFunction() {
            @Override
            public double getNextY(double x) {
                HashMap<Integer,Integer> map = new HashMap<>(100);
                Dataset<Integer,Integer> set = new RandomIntegerData((int)x);
                Benchmark<Integer,Integer> benchmark = new ReadWriteBenchmark<>(set,map);
                BenchmarkSummary summary = new BenchmarkSummary(benchmark,5);
                //   System.out.println("Done");
                return summary.getMean()/(double) x * 1000; //get time per insertion, and get nanoseconds
            }
        });
        System.out.println(helper.plot(10,100000,1000));


    }
}
