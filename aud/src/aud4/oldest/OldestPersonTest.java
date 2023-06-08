package aud4.oldest;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OldestPersonTest {

    public static List<Person> readData(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.lines()
                .map(line -> new Person(line))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        File file = new File("C:\\java\\napredno\\aud\\src\\aud4\\oldest\\files\\people");
        try {
            List<Person> people = readData(new FileInputStream(file));

            Collections.sort(people);
            //NACIN 1
            System.out.println(people.get(people.size() - 1));


            //NACIN 2
            if (people.stream().max(Comparator.naturalOrder()).isPresent()){
                System.out.println(people.stream().max(Comparator.naturalOrder()).get());
            }
            //Comparator.naturalOrder() vrakja komparator od najgolem do najmal
            //Comparator.revrseOrder() vrakja od najmal do najgolem
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
