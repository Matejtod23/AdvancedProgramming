package vleznaIzad1;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class LineProcessorStream{
    List<String> lines;

    public LineProcessorStream() {
        lines = new ArrayList<>();
    }

    private int countC(String line, char c){
        int counter = 0;
        for (char character: line.toLowerCase().toCharArray()){
            if (character==c)
                counter++;
        }
        return counter;
    }

    public void readLines(InputStream in, OutputStream out, char c){
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        lines = br.lines().collect(Collectors.toList()); // gi sobira vo edna lista od lines

        Comparator<String> comparator = Comparator.comparing(str -> countC(str,c));

        String max = lines.stream()
                .max(comparator.thenComparing(Comparator.naturalOrder()))
                .orElse("");


        PrintWriter pw = new PrintWriter(out);
        pw.println(max);
        pw.flush();
    }
}

public class LineProcessorWithStream {
    public static void main(String[] args) {
        LineProcessor lineProcessor = new LineProcessor();

        lineProcessor.readLines(System.in, System.out, 'a');
    }
}
