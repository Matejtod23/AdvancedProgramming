package kolok2.zad1;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Name implements Comparable<Name>{
    String name;

    public Name(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int uniqueChars(){
        return (int) IntStream.range(0, name.length()).map(i -> name.toLowerCase().charAt(i)).distinct().count();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Name o) {
        return name.compareTo(o.name);
    }
}

class Names {
    TreeMap<Name, Integer> namesWithFrequency;
    ArrayList<String> names;

    public Names() {
        namesWithFrequency = new TreeMap<>();
        names = new ArrayList<>();
    }

    public void addName(String name){
        names.add(name);
        Name n = new Name(name);
        namesWithFrequency.putIfAbsent(n, 0);
        namesWithFrequency.computeIfPresent(n, (k, v) -> {
            v++;
            return v;
        });
    }

    public void printN(int n) {
        namesWithFrequency.entrySet().stream().filter(entry -> {
            return entry.getValue() >= n;
        }).forEach(entry -> {
            int occurencies = entry.getValue();
            int distinct = entry.getKey().uniqueChars();
            System.out.println(entry.getKey() + " ("+occurencies+") " + distinct);
        });
    }

    public String findName(int len, int x) {
        String nameToR = "";
        List<Name> listToGetBack = new ArrayList<>();
       listToGetBack = namesWithFrequency.keySet().stream().filter(name -> {
            return name.name.length() < len;
        }).collect(Collectors.toList());
       if (x < listToGetBack.size()){
           return listToGetBack.get(x).name;
       }else {
           int counter = 0;
           for (int i = 0; i < listToGetBack.size(); i++)
           {
               if (i == listToGetBack.size() - 1 && counter != x){
                   i = -1;
               }
               if (counter == x){
                    nameToR = listToGetBack.get(i).name;
                    break;
               }
               counter++;
           }
       }
       return nameToR;
    }
}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

// vashiot kod ovde