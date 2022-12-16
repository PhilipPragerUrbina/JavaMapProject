package MegaMaps;


import MegaMaps.Maps.TreeMap;
import MegaMaps.Testing.BenchmarkSummary;
import MegaMaps.Testing.Benchmarks.Benchmark;
import MegaMaps.Testing.Benchmarks.InsertionBenchmark;
import MegaMaps.Testing.Datasets.Dataset;
import MegaMaps.Testing.Datasets.RandomIntegerData;
import MegaMaps.Maps.HashMap;
import MegaMaps.Testing.Tests.InsertReadTest;
import MegaMaps.Testing.Tests.Test;
import MegaMaps.Utils.DesmosHelper;


public class Main {

    public static void main(String[] args) throws Exception {
        HashMap<Integer,Integer> map = new HashMap<>(1000);
        Dataset<Integer,Integer> set = new RandomIntegerData(10000);
        Test<Integer,Integer> test = new InsertReadTest<>(set,map);
        System.out.println(test.run());


        DesmosHelper helper = new DesmosHelper(new DesmosHelper.plotFunction() {
            @Override
            public double getNextY(double x) {
                TreeMap<Integer,Integer> map = new TreeMap<>(100);
                Dataset<Integer,Integer> set = new RandomIntegerData((int)x);
                Benchmark<Integer,Integer> benchmark = new InsertionBenchmark<>(set,map);
                BenchmarkSummary summary = new BenchmarkSummary(benchmark,5);
                return summary.getMean()/(double) x * 1000; //get time per insertion, and get nanoseconds
            }
        });
        System.out.println(helper.plot(10,100000,1000));


    }
}
