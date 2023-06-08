package vleznaIzad3;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class Racer implements Comparable<Racer>{

    private String name;
    private String lap;

    public Racer(String name, String lap) {
        this.name = name;
        this.lap = lap;
    }

    public static Racer createRacer(String line){
        String[] parts = line.split("\\s+");
        String name = parts[0];
        String min = parts[1];
        for (int i = 2;i<parts.length;i++){
            if (min.compareTo(parts[i])>0){
                min = parts[i];
            }
        }
        return new Racer(name, min);
    }


    @Override
    public int compareTo(Racer o) {
        return lap.compareTo(o.lap);
    }

    @Override
    public String toString() {
        return String.format("%-10s\t%10s",name, lap);
    }
}

class F1Race {

    List<Racer> racers = new ArrayList<>();

    public F1Race(List<Racer> racers) {
        this.racers = racers;
    }

    public F1Race() {

    }

    public void readResults(InputStream in) {
        racers = new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(l -> Racer.createRacer(l))
                .collect(Collectors.toList());
    }

    public void printSorted(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        racers = racers.stream().sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        for (int i = 0; i <= racers.size(); i++){
            pw.format("%d. %s",i+1,racers.get(i));
        }
        pw.flush();
    }
    // vashiot kod ovde

}