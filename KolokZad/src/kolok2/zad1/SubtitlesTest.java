package kolok2.zad1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

class Subtitles {
    List<String> listOfLines;
    public Subtitles() {
        listOfLines = new ArrayList<>();
    }


    public int loadSubtitles(InputStream in) {
        BufferedReader bw = new BufferedReader(new InputStreamReader(in));
        bw.lines().forEach(line -> listOfLines.add(line));

        int length = (int) listOfLines.stream().filter(line -> line.equals("")).count();

        return length+1;
    }

    public void print() {
        listOfLines.stream().forEach(System.out::println);
        System.out.println();
    }

    public void shift(int shift) {
        listOfLines = listOfLines.stream().map(line -> {
            if (line.contains("-->")){
                String[] parts = line.split(" --> ");
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss,SSS");
                String num1;
                String num2;
                try {
                   num1 = sdf.format((sdf.parse(parts[0]).getTime() + shift));
                   num2 = sdf.format((sdf.parse(parts[1]).getTime() + shift));
                    System.out.println(num1);
                    System.out.println(num2);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                return line = num1 + " --> " + num2;
            }
            return line;
        }).collect(Collectors.toList());

    }
}

public class SubtitlesTest {
    public static void main(String[] args) {
        Subtitles subtitles = new Subtitles();
        int n = subtitles.loadSubtitles(System.in);
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

// Вашиот код овде
