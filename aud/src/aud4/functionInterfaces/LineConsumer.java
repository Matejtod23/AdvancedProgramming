package aud4.functionInterfaces;

import java.util.function.Consumer;

public class LineConsumer implements Consumer<String> {
    private int lines = 0;
    private int words = 0;
    private int chars = 0;
    @Override
    public void accept(String line) {
        ++lines;
        words += line.split("\\s+").length;
        chars += line.length();
    }
    @Override
    public String toString() {
        return String.format("Lines: " + lines + ", Words: " + words + ", Chars: " + chars);
    }

}
