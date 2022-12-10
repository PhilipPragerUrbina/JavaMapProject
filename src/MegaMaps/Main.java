package MegaMaps;

import MegaMaps.Benchmarks.SimpleBenchMark;
import MegaMaps.Maps.HashMap;
import MegaMaps.Maps.LoopMap;
import MegaMaps.Utils.Pair;


public class Main {

    public static void main(String[] args) throws Exception {
        HashMap<String,Integer> map = new HashMap<>(1000);
        SimpleBenchMark bench = new SimpleBenchMark(map);

        System.out.println("Time(ms) hashmap: "+ bench.run(5000000));
        System.out.println("Time(ms) hashmap2: "+ bench.run(5000000));

        Map<String,Integer> map2 = new LoopMap<>();
        SimpleBenchMark bench2 = new SimpleBenchMark(map2);

        System.out.println("Time(ms) simple: "+ bench2.run(50000));


        for (Pair<String,Integer> a: (HashMap<String,Integer>)map) {
            System.out.println(a);
        }

    }
}
