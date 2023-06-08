package kolok2.zad1;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TestClass {
    int counter = 0;
    public static void main(String[] args) {
       TestClass t = new TestClass();
       List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("my");
        list.add("name");
        list.add("is");
        list.add("Matej");
        t.print(list);
    }
    public  void print(List<String> list){
        for (String line: list) {
            if (counter == 2) {
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                counter = 0;
            }
            System.out.println(line);
            counter++;
        }
    }
}
