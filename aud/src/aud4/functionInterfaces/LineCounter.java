package aud4.functionInterfaces;

public class LineCounter {

    private int lines;
    private int words;
    private int chars;

    public LineCounter(int lines, int words, int chars) {
        this.lines = lines;
        this.words = words;
        this.chars = chars;
    }

    public LineCounter(String line){
        ++lines;
        words = line.split("\\s+").length;
        chars = line.length();
    }

    @Override
    public String toString() {
        return String.format("Lines: " + lines + ", Words: " + words + ", Chars: " + chars);
    }

    public LineCounter sum(LineCounter lineCounter){
        return new LineCounter(this.lines + lineCounter.lines,
                this.words + lineCounter.words,
                this.chars + lineCounter.chars);
    }
}
