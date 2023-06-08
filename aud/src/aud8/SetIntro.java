package aud8;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetIntro {
    public static void main(String[] args) {
        //mnozestva -- sets -- nema duplikati elementi
        Set<String> set;

        //1
//        set = new HashSet<String>();
//        String s1 = "Stefan";
//        String s2 = "Stefan";

       //TreeSet
        set = new TreeSet<>((l,r) -> l.compareToIgnoreCase(r));
        String s1 = "Stefan";
        String s2 = "stefan";
        set.add(s1);
        set.add(s2);
        set.add("NP");
        set.add("NApredno programiranje");
        System.out.println(set);
    }
}
