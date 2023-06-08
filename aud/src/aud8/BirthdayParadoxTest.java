package aud8;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

class Trial{
    int people;
    Set<Integer> birthdaySet;
    static Random RANDOM = new Random();

    public Trial(int people) {
        this.people = people;
        birthdaySet = new HashSet<>();
    }

    boolean run(){
        for (int i = 0; i < people; i++){
            int birthday = RANDOM.nextInt(365)+1;
            if (birthdaySet.contains(birthday)){
                return true;
            }else {
                birthdaySet.add(birthday);
            }
        }
        return false;
    }
}

class Experiment{
    int people;
    static int TRAILS = 5000;

    public Experiment(int people) {
        this.people = people;
    }

    public double run(){
//        int count = 0;
//        for (int i = 0; i < TRAILS; i++){
//            Trial trial = new Trial(people);
//            if (trial.run()){
//                count++;
//            }
//        }
        int count = (int)IntStream.range(0, TRAILS)
                .mapToObj(i -> new Trial(people))
                .map(trial -> trial.run())
                .filter(b -> b)
                .count();
        return (float)count/TRAILS;
    }

    @Override
    public String toString() {
        return String.format("For %d people, the probability of two birthdays is about %.5f",people,run());
    }
}

public class BirthdayParadoxTest {
    public static void main(String[] args) {
        for (int i = 2; i <= 50; i++){
            Experiment experiment = new Experiment(i);
            System.out.println(experiment.toString());
        }
    }
}
