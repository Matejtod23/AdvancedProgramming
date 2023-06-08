package aud9;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapIntro {
    public static void main(String[] args) {
        Map<String, Integer> map;

        //HashMap
        //O(1) kompleksnost za dodavanje,brisenje...
        //redosledot e spored hascode na elementot
        //map = new HashMap<>();

        //LinkedHashMap
        //se zadrzuva redosledot na vnesuvanje na elemetntite
        //map = new LinkedHashMap<>();

        //TreeMap
        //redosledot kje bide sortiran spored comparable vred na klasata
        map = new TreeMap<>();
        map.put("NP1", 1);
        map.put("NP2", 2);
        map.put("AA",2);
        map.put("NP3", 3);
        System.out.println(map);
    }
}
