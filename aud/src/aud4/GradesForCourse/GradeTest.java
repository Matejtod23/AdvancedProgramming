package aud4.GradesForCourse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GradeTest {
    public static void main(String[] args) {
        Course course = new Course();
        File inputFile = new File("C:\\java\\napredno\\aud\\src\\aud4\\GradesForCourse\\files\\students");
        File outputFile = new File("C:\\java\\napredno\\aud\\src\\aud4\\GradesForCourse\\files\\result");

        try {
            course.readData(new FileInputStream(inputFile));
            System.out.println("Printin sorted students");
            course.printSortedData(System.out);

            System.out.println("Printin detailed students");
            course.printDetailledData(System.out);

            System.out.println("Printin distribution students");
            course.printDistribution(System.out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
