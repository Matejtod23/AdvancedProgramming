//package kolok2.zad1;
//
//import java.util.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//class Names {
//    private TreeMap<String, Integer> namesCount;
//    private ArrayList<String> uniqueNames;
//
//    public Names() {
//        namesCount = new TreeMap<>();
//        uniqueNames = new ArrayList<>();
//    }
//
//    public void addName(String name) {
//        if (namesCount.containsKey(name)) {
//            namesCount.put(name, namesCount.get(name) + 1);
//        } else {
//            namesCount.put(name, 1);
//            uniqueNames.add(name);
//        }
//    }
//
//    public void printN(int n) {
//        for (Map.Entry<String, Integer> entry : namesCount.entrySet()) {
//            if (entry.getValue() >= n) {
//                System.out.println(entry.getKey() + " (" + entry.getValue() + ") " + uniqueLetterCount(entry.getKey()));
//            }
//        }
//    }
//
//    private int uniqueLetterCount(String name) {
//        HashMap<Character, Boolean> uniqueChars = new HashMap<>();
//        for (int i = 0; i < name.length(); i++) {
//            uniqueChars.put(name.charAt(i), true);
//        }
//        return uniqueChars.size();
//    }
//
//    public String findName(int len, int x) {
//        ArrayList<String> filteredNames = new ArrayList<>();
//        for (String name : uniqueNames) {
//            if (name.length() < len) {
//                filteredNames.add(name);
//            }
//        }
//        filteredNames.sort((n1, n2) -> n1.compareTo(n2));
//
//        int index = x % filteredNames.size();
//        return filteredNames.get(index);
//    }
//}
//
//
//public class NamesTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        Names names = new Names();
//        for (int i = 0; i < n; ++i) {
//            String name = scanner.nextLine();
//            names.addName(name);
//        }
//        n = scanner.nextInt();
//        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
//        names.printN(n);
//        System.out.println("===== FIND NAME =====");
//        int len = scanner.nextInt();
//        int index = scanner.nextInt();
//        System.out.println(names.findName(len, index));
//        scanner.close();
//
//    }
//}
//
//// vashiot kod ovde