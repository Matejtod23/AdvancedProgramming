package aud4.GradesForCourse;

import aud4.oldest.Person;

public class Studnet implements Comparable<Studnet>{
    private String lastname;
    private String firstname;
    private int exam1;
    private int exam2;
    private int exam3;
    char grade;

    public Studnet(String lastname, String firstname, int exam1, int exam2, int exam3) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.exam1 = exam1;
        this.exam2 = exam2;
        this.exam3 = exam3;
        setGrade();
    }

    public char getGrade() {
        return grade;
    }

    public double totalPoints() {
        return exam1 * 0.25 + exam2 * 0.3 + 045 * exam3;
    }

    public void setGrade() {
        double points = totalPoints();

        if (points >= 90) {
            this.grade = 'A';
        } else if (points >= 80) {
            this.grade = 'B';
        } else if (points >= 70) {
            this.grade = 'C';
        } else if (points >= 60) {
            this.grade = 'D';
        } else if (points >= 50) {
            this.grade = 'E';
        } else this.grade = 'F';
    }

    public static Studnet createStudent(String line){
        String[] parts = line.split(":");
        return new Studnet(parts[1],
                            parts[0],
                            Integer.parseInt(parts[2]),
                            Integer.parseInt(parts[3]),
                            Integer.parseInt(parts[4]));
    }


    @Override
    public String toString() {
        return lastname + " " + firstname + " " + grade;
    }

    public String printFullInformation(){
        return String.format("%s %s %d %d %d %.2f %c", lastname, firstname, exam1, exam2, exam3, totalPoints(), grade);
    }


    @Override
    public int compareTo(Studnet o) {
        return Character.compare(this.grade, o.grade); // ako se svrtat ovie odnosno o.grade da e prvo kje e vo opagjacki redosled
    }
}
