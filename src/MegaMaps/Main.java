package MegaMaps;

import MegaMaps.Benchmarks.SimpleBenchMark;
import MegaMaps.Maps.LoopMap;



public class Main {

    public static void main(String[] args) throws Exception {
        Map<String,Integer> map = new LoopMap<>();
        SimpleBenchMark bench = new SimpleBenchMark(map);

        System.out.println("Time(ms): "+ bench.run(10000));

    }
}
