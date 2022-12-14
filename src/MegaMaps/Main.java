package MegaMaps;


import MegaMaps.Testing.Datasets.Dataset;
import MegaMaps.Testing.Datasets.RandomIntegerData;
import MegaMaps.Maps.HashMap;
import MegaMaps.Testing.Tests.InsertReadTest;
import MegaMaps.Testing.Tests.Test;


public class Main {

    public static void main(String[] args) throws Exception {
        HashMap<Integer,Integer> map = new HashMap<>(1000);
        Dataset<Integer,Integer> set = new RandomIntegerData(10000);
        Test<Integer,Integer> test = new InsertReadTest<>(set,map);
        System.out.println(test.run());


    }
}
