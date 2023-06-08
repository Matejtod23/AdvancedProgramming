package aud4.functionInterfaces;

import java.io.*;
import java.util.Scanner;

public class WordCount {

    public static void readDataWithScanner(InputStream inputStream) {
        int lines = 0, word = 0, chars = 0;
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines++;
            word += line.split("\\s+").length;
            chars += line.length();
        }
        System.out.println("Lines: " + lines + ", Words: " + word + ", Chars: " + chars);
    }

    public static void readDataWithBufferReader(InputStream inputStream) throws IOException {
        int lines = 0, word = 0, chars = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lines++;
            word += line.split("\\s+").length;
            chars += line.length();
        }
        System.out.println("Lines: " + lines + ", Words: " + word + ", Chars: " + chars);
    }

    public static void readDataWithBufferdReaderMapAndReduce(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        LineCounter result = bufferedReader.lines()
                .map(l -> new LineCounter(l))
                .reduce(
                        new LineCounter(0, 0, 0),
                        (left, right) -> left.sum(right)
                );
        System.out.println(result);
    }

    public static void readDataWithBufferedReaderAndConsumer(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        LineConsumer lineConsumer= new LineConsumer();
        bufferedReader.lines().forEach(lineConsumer);
        System.out.println(lineConsumer);
    }

    public static void main(String[] args) {
        File file = new File("C:\\java\\napredno\\aud\\src\\aud4" +
                "\\functionInterfaces\\files\\text.txt");
        try {
            readDataWithScanner(new FileInputStream(file));
            readDataWithBufferReader(new FileInputStream(file));
            readDataWithBufferdReaderMapAndReduce(new FileInputStream(file));
            readDataWithBufferedReaderAndConsumer(new FileInputStream(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
