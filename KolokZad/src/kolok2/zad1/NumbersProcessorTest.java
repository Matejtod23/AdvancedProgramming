package kolok2.zad1;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Row{
    List<Integer> numbers;

    Row(String line){
        numbers = Arrays.stream(line.split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public boolean condition(){
        int max  = numbers.stream().mapToInt(i -> i).max().getAsInt();

        Map<Integer, Long> countingMap = numbers.stream().collect(Collectors.groupingBy(
                i -> i,
                Collectors.counting()
        ));

        int frequencyOfMax = countingMap.get(max).intValue();
        int maxFrequency = countingMap.values().stream()
                .mapToInt(Long::intValue)
                .max()
                .getAsInt();

        return frequencyOfMax == maxFrequency;
    }

    public int max (){
        return numbers.stream().mapToInt(i->i).max().getAsInt();
    }
}

class NumberProcessor{

    List<Row> list;
    public NumberProcessor() {
        list = new ArrayList<>();
    }


    public void readRows(InputStream in) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        list = bf.lines().map(Row::new)
                .collect(Collectors.toList());
    }


    public void printMaxFromRows(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);

        list.stream()
                .filter(Row::condition)
                .map(row -> row.max())
                .forEach(maxNum -> pw.println(maxNum));

        pw.flush();
        pw.close();
    }
}

public class NumbersProcessorTest {
    public static void main(String[] args) {
        NumberProcessor numberProcessor = new NumberProcessor();

        numberProcessor.readRows(System.in);

        numberProcessor.printMaxFromRows(System.out);
    }
}
