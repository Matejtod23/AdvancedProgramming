//package kolok2.zad1;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//import java.util.stream.Collectors;
//
//class LineWords{
//    String line;
//
//    public LineWords(String line) {
//        this.line = line;
//    }
//
//    public void replace(){
//        line.replaceAll("[^a-zA-Z_0-9]" ,"");
//    }
//}
//class TextProcessor{
//
//    Map<String, List<LineWords>> lines;
//    public TextProcessor() {
//        lines = new TreeMap<>();
//    }
//
//    public void readText(InputStream in) {
//        BufferedReader bw = new BufferedReader(new InputStreamReader(in));
//
//    }
//}
//
//class CosineSimilarityCalculator {
//    public static double cosineSimilarity (Collection<Integer> c1, Collection<Integer> c2) {
//        int [] array1;
//        int [] array2;
//        array1 = c1.stream().mapToInt(i -> i).toArray();
//        array2 = c2.stream().mapToInt(i -> i).toArray();
//        double up = 0.0;
//        double down1=0, down2=0;
//
//        for (int i=0;i<c1.size();i++) {
//            up+=(array1[i] * array2[i]);
//        }
//
//        for (int i=0;i<c1.size();i++) {
//            down1+=(array1[i]*array1[i]);
//        }
//
//        for (int i=0;i<c1.size();i++) {
//            down2+=(array2[i]*array2[i]);
//        }
//
//        return up/(Math.sqrt(down1)*Math.sqrt(down2));
//    }
//}
//
//public class TextProcessorTest {
//
//    public static void main(String[] args) {
//        TextProcessor textProcessor = new TextProcessor();
//
//        textProcessor.readText(System.in);
//
//        System.out.println("===PRINT VECTORS===");
//        textProcessor.printTextsVectors(System.out);
//
//        System.out.println("PRINT FIRST 20 WORDS SORTED ASCENDING BY FREQUENCY ");
//        textProcessor.printCorpus(System.out,  20, true);
//
//        System.out.println("PRINT FIRST 20 WORDS SORTED DESCENDING BY FREQUENCY");
//        textProcessor.printCorpus(System.out, 20, false);
//
//        System.out.println("===MOST SIMILAR TEXTS===");
//        textProcessor.mostSimilarTexts(System.out);
//    }
//}