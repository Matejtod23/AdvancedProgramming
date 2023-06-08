package aud4.functionInterfaces;

import java.util.Random;
import java.util.function.*;

public class FunctionalInterfaces {
    public static void main(String[] args) {
        //predikat se koristi za filter(),anymach(),findFirst()
        Predicate<Integer> LessThan100 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer < 100;
            }
        };
        Predicate<Integer> lessThan100 = integer -> integer < 100;

        Supplier<Integer> integerSupplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(1000);
            }
        };
        Supplier<Integer> integerSupplier1 = () -> new Random().nextInt(1000);

        Consumer<String> stringConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        Consumer<String> stringConsumer1 = s -> System.out.println(s);

        Function<Integer, String> FormatedNumberString = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.format("%d\n", integer);
            }
        };

        Function<Integer, String> formatedNumberString = num -> String.format("%d\n", num);
    }

    BiFunction<Integer, Integer, String> sumNumbersAnyFormat = new BiFunction<Integer, Integer, String>() {
        @Override
        public String apply(Integer integer, Integer integer2) {
            return String.format("%d + %d = %d", integer, integer2, integer + integer2);
        }
    };

    BiFunction<Integer, Integer, String> SumNumbersAnyFormat = (x, y) ->
            String.format("%d + %d = %d", x, y, x + y);
}
