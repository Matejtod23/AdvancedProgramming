import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

class TermFrequency{
    private int totalWords;
    private List<String> listOfWords;

    TermFrequency(InputStream is, String[] stopWords){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        this.listOfWords = br.lines().flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(word -> word.toLowerCase())
                .map(word -> {
                    if (word.length() == 0)
                        return word;
                    if (word.charAt(word.length() - 1) == ',' || word.charAt(word.length() - 1) == '.')
                        word = word.substring(0, word.length()-1);
                    return word;
                })
                .filter(word -> {
                    return !Arrays.asList(stopWords).contains(word) && word.length()>0;
                })
                .collect(Collectors.toList());
    
        totalWords = listOfWords.size();
    }
    
    public int countTotal(){
        return totalWords;
    }

    public int countDistinct() {
        return (int)listOfWords.stream().distinct().count();
    }

    public List<String> mostOften(int k) {
        List<String> list = new ArrayList<>();
        TreeMap<String, Integer> map = new TreeMap<>();
        listOfWords.stream().forEach(word -> {
            int count = (int)listOfWords.stream().filter(w->w.equals(word)).count();
            map.put(word, count);
        });
        list = map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).map(Entry::getKey).limit(k).collect(Collectors.toList());

        return list;
    }
}

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde
