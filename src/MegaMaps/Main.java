package MegaMaps;

import MegaMaps.Benchmarks.SimpleBenchMark;
import MegaMaps.Maps.HashMap;
import MegaMaps.Maps.LoopMap;



public class Main {

    public static void main(String[] args) throws Exception {
        Map<String,Integer> map = new HashMap<>(1000);
        SimpleBenchMark bench = new SimpleBenchMark(map);

        System.out.println("Time(ms) hashmap: "+ bench.run(50000)); //57 ms

        Map<String,Integer> map2 = new LoopMap<>();
        SimpleBenchMark bench2 = new SimpleBenchMark(map2);

        System.out.println("Time(ms) simple: "+ bench2.run(50000)); //12920 ms

    }
}
