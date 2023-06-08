package vleznaIzad1;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Line implements Comparable<Line>{
    String line;
    char c;

    public Line(String line, char c) {
        this.line = line;
        this.c = c;
    }

    private int countC(){
        int counter = 0;
        for (char character: line.toLowerCase().toCharArray()){
            if (character==this.c)
                counter++;
        }
        return counter;
    }


    @Override
    public int compareTo(Line o) {
        return Integer.compare(this.countC(), o.countC());
    }

    @Override
    public String toString() {
        return line;
    }
}

class LineProcessor{

    List<Line> lines;
    public LineProcessor(){
        lines = new ArrayList<>();
    }

    public void readLines(InputStream in, OutputStream out, char c) {
        Scanner sc = new Scanner(in);
        PrintWriter pw = new PrintWriter(out);

        while (sc.hasNextLine()){
            String str = sc.nextLine();
            Line line = new Line(str, c);
            lines.add(line);
        }

        sc.close();

        Line max = lines.get(0);
        for (Line line: lines){
            if (line.compareTo(max) >= 0){
                max = line;
            }
        }
        pw.println(max);
        pw.flush();
    }
}

public class LineProcessorTest {
    public static void main(String[] args) {
        LineProcessor lineProcessor = new LineProcessor();

        lineProcessor.readLines(System.in, System.out, 'a');
    }
}
