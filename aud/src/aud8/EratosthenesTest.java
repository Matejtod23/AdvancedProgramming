package aud8;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Eratosthens{
    private boolean isPrime(int num){
//        for (int i = 2; i < num; i++){
//            if (num%i == 0){
//                return false;
//            }
//        }

        return IntStream.range(2, num).noneMatch(i -> num%i == 0);
    }
    void process(List<Integer> numbers){
        for (int i = 0; i < numbers.size(); i++){
            if (isPrime(numbers.get(i))){
                for (int j = i+1; j < numbers.size(); j++){
                    if (numbers.get(j)%numbers.get(i)==0){
                        numbers.remove(j);
                        --j;
                    }
                }
            }
        }
    }
}

public class EratosthenesTest {


    public static void main(String[] args) {
        List<Integer> list = IntStream.range(2, 1000).boxed().collect(Collectors.toList());
        Eratosthens eratosthens = new Eratosthens();
        eratosthens.process(list);
        System.out.println(list);
    }
}
