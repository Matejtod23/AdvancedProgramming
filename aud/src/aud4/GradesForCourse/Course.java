package aud4.GradesForCourse;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Course {
    private List<Studnet> students;

    public Course() {
        students = new ArrayList<>();
    }
    public void readData(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.students = bufferedReader.lines()
                .map(line -> Studnet.createStudent(line))
                .collect(Collectors.toList());
    }
    public void printSortedData(OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(outputStream);
        this.students.stream().sorted().forEach(s -> printWriter.println(s));
        printWriter.flush();
    }
    public void printDetailledData(OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(outputStream);
        this.students.stream().sorted().forEach(s -> printWriter.println(s.printFullInformation()));
        printWriter.flush();
    }
    public void printDistribution(OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(outputStream);
        int[] gradeDistribution = new int[6];
        for (Studnet s: students)
            gradeDistribution[s.getGrade() - 'A']++;

        for (int i = 0; i<6; i++){
            printWriter.printf("%c -> %d\n",i + 'A', gradeDistribution[i]);
        }
        printWriter.flush();
    }
}
