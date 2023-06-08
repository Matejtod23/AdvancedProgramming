package kolok2.zad1;


import java.util.*;
import java.util.stream.Collectors;

class OperationNotAllowedException extends Exception{
    public OperationNotAllowedException(int t, String id) {
        super(String.format("Term %d is not possible for student with ID %s",t, id));
    }
    public OperationNotAllowedException(String id, int t) {
        super(String.format("Student %s already has 3 grades in term %d",id, t));
    }
}
class Grade implements Comparable<Grade> {
    int term;
    String courseName;
    int grade;

    public Grade(int term, String courseName, int grade) {
        this.term = term;
        this.courseName = courseName;
        this.grade = grade;
    }

    public int getTerm() {
        return term;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getGrade() {
        return grade;
    }

    @Override
    public int compareTo(Grade o) {
        return courseName.compareTo(o.courseName);
    }
}

abstract class Student implements Comparable<Student>{
    String id;

    TreeMap<Integer, List<Grade>> grades;
    int yearsOfStudies;


    public Student(String id, int years) {
        this.id = id;
        grades = new TreeMap<>();
        this.yearsOfStudies = years;
    }

    public int getYearsOfStudies() {
        return yearsOfStudies;
    }

    public String getId() {
        return id;
    }


    public TreeMap<Integer, List<Grade>> getGrades() {
        return grades;
    }

    public abstract int setDuration();
    public abstract boolean isGraduated();
    public abstract double averageGrade();

    public abstract double avarageGradeForTerm(int term);
    public abstract int coursesForTerm(int t);

    public abstract int numOfPassedExams();
}

class ThreeYearStudent extends Student{
    int yearsOfStudies;

    public ThreeYearStudent(String id, int years) {
        super(id, years);
        setDuration();
        yearsOfStudies = setDuration();
    }

    @Override
    public int setDuration() {
        return 3;
    }



    @Override
    public boolean isGraduated() {
        int numG = grades.values().stream().mapToInt(grade -> {
            return grade.stream().mapToInt(Grade::getGrade).sum();
        }).sum();
        return numG >= 18;
    }

    @Override
    public double averageGrade() {
        int numG = numGrades();
        int sumGrades = sumGrades();
        return sumGrades / (float) numG;
    }

    @Override
    public double avarageGradeForTerm(int t) {
        return grades.get(t).stream().mapToInt(Grade::getGrade).average().getAsDouble();
    }

    @Override
    public int coursesForTerm(int t) {
        return grades.get(t).size();
    }

    @Override
    public int numOfPassedExams() {
        return grades.values().stream().mapToInt(value -> {
            return value.stream().filter(grade -> grade.grade > 5).collect(Collectors.toList()).size();
        }).sum();
    }

    public int numGrades(){
        int num = grades.values().stream().mapToInt(line -> {
            return line.size();
        }).sum();
        return num;
    }

    public int sumGrades(){
        int sum = grades.values().stream().mapToInt(list ->{
            return list.stream().mapToInt(g -> g.grade).sum();
        }).sum();
        return sum;
    }

    @Override
    public String toString() {
        if (grades.values().size() == 0){
            return String.format("Student: %s Courses passed: %d Average grade: 5.00",id,numOfPassedExams(), 5);
        }else
            return String.format("Student: %s Courses passed: %d Average grade: %.2f",id,numOfPassedExams(),averageGrade());
    }

    @Override
    public int compareTo(Student o) {
        if (numOfPassedExams() == o.numOfPassedExams()){
            return Double.compare(averageGrade(), o.averageGrade());
        }else {
            return Integer.compare(numOfPassedExams(), o.numOfPassedExams());
        }
    }
}

class FourYearStudent extends Student{
    int yearsOfStudies;

    public FourYearStudent(String id, int years) {
        super(id, years);
        this.yearsOfStudies = setDuration();
    }

    @Override
    public int setDuration() {
        return 4;
    }

    @Override
    public boolean isGraduated() {
        int numG = grades.values().stream().mapToInt(grade -> {
            return grade.stream().mapToInt(Grade::getGrade).sum();
        }).sum();
        return numG >= 24;
    }
    @Override
    public double averageGrade() {
        int numG = numGrades();
        int sumGrades = sumGrades();
        return sumGrades / (float) numG;
    }
    public int numGrades(){
        int num = grades.values().stream().mapToInt(line -> {
            return line.size();
        }).sum();
        return num;
    }

    public int sumGrades(){
        int sum = grades.values().stream().mapToInt(list ->{
            return list.stream().mapToInt(g -> g.grade).sum();
        }).sum();
        return sum;
    }

    @Override
    public double avarageGradeForTerm(int t) {
        return grades.get(t).stream().mapToInt(Grade::getGrade).average().getAsDouble();
    }

    @Override
    public int coursesForTerm(int t) {
        return grades.get(t).size();
    }
    @Override
    public String toString() {
        if (grades.values().size() == 0){
            return String.format("Student: %s Courses passed: %d Average grade: 5.00",id,numOfPassedExams());
        }else
            return String.format("Student: %s Courses passed: %d Average grade: %.2f",id,numOfPassedExams(),averageGrade());
    }

    @Override
    public int numOfPassedExams() {
        return grades.values().stream().mapToInt(value -> {
            return value.stream().filter(grade -> grade.grade > 5).collect(Collectors.toList()).size();
        }).sum();
    }

    @Override
    public int compareTo(Student o) {
        if (numOfPassedExams() == o.numOfPassedExams()){
            return Double.compare(averageGrade(), o.averageGrade());
        }else {
            return Integer.compare(numOfPassedExams(), o.numOfPassedExams());
        }
    }

}


class Faculty{

    TreeMap<String, Student> students;
    TreeSet<Student> graduatedS;
    public Faculty() {
        students = new TreeMap<>();
        graduatedS = new TreeSet<>();
    }

    void addStudent(String id, int yearsOfStudies) {
        if (yearsOfStudies == 3){
            ThreeYearStudent s = new ThreeYearStudent(id, yearsOfStudies);
            students.put(id, s);
        }else {
            FourYearStudent s = new FourYearStudent(id, yearsOfStudies);
            students.put(id, s);
        }
    }

    void addGradeToStudent(String studentId, int termS, String courseName, int gradeToAdd) throws OperationNotAllowedException {
        Student curr = students.get(studentId);
        if ((curr.yearsOfStudies == 3 && termS > 6) ||
        curr.yearsOfStudies == 4 && termS > 8){
            throw new OperationNotAllowedException(termS, studentId);
        }
        if (curr.grades.size() == 0){
            Grade g = new Grade(termS, courseName, gradeToAdd);
            students.get(studentId).grades.put(termS, new ArrayList<>());
            students.get(studentId).grades.get(termS).add(g);
        }
        else {
            if (curr.grades.get(termS) == null){
                Grade g = new Grade(termS, courseName, gradeToAdd);
                students.get(studentId).grades.put(termS, new ArrayList<>());
                students.get(studentId).grades.get(termS).add(g);
            }
            else {
                int gradesForThisTerm = curr.grades.get(termS).size();
                if (gradesForThisTerm >= 3) {
                    throw new OperationNotAllowedException(studentId, termS);
                } else {
                    Grade g = new Grade(termS, courseName, gradeToAdd);
                    students.get(studentId).grades.get(termS).add(g);
                }
            }
        }
        if (students.get(studentId).isGraduated()){
            graduatedS.add(students.get(studentId));
        }
    }

    public String getFacultyLogs() {
        StringBuilder sb = new StringBuilder();
        students.values().stream().filter(student -> student.isGraduated())
                .forEach(student -> {
                    String s = String.format("Student with ID %s graduated with average grade %.2f in %d years.\n",student.id,student.averageGrade(),student.yearsOfStudies);
                    sb.append(s);
                });
        graduatedS.stream().forEach(student -> {
            students.remove(student.id);
        });
        return sb.substring(0, sb.length()-1);
    }

    String getDetailedReportForStudent(String id) {
        Student curr = students.get(id);
        StringBuilder sb = new StringBuilder();
        sb.append("Student: "+id+"\n");
        students.get(id).grades.entrySet().stream().forEach(entry -> {
            sb.append("Term "+entry.getKey()+":\n");
            sb.append("Courses: " + curr.coursesForTerm(entry.getKey())+"\n");
            sb.append("Average grade for term: "+curr.avarageGradeForTerm(entry.getKey())+"\n");
        });
        sb.append(String.format("Average grade: %.2f\n", students.get(id).averageGrade()));

        students.get(id).grades.values().forEach(g -> {
            g.forEach(grade -> {
                sb.append(grade.courseName + ", ");
            });
        });
        return sb.toString();
    }

    void printFirstNStudents(int n) {
        Comparator comparator = Comparator.comparing(Student::numOfPassedExams)
                        .thenComparing(Student::averageGrade)
                        .thenComparing(Student::getId);
        students.values().stream().sorted(comparator.reversed()).limit(n).forEach(System.out::println);
    }

    void printCourses() {
    }

}

public class FacultyTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        if (testCase == 1) {
            System.out.println("TESTING addStudent AND printFirstNStudents");
            Faculty faculty = new Faculty();
            for (int i = 0; i < 10; i++) {
                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
            }
            faculty.printFirstNStudents(10);

        } else if (testCase == 2) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            try {
                faculty.addGradeToStudent("123", 7, "NP", 10);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
            try {
                faculty.addGradeToStudent("1234", 9, "NP", 8);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } else if (testCase == 3) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (testCase == 4) {
            System.out.println("Testing addGrade for graduation");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            int counter = 1;
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            counter = 1;
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
            faculty.printFirstNStudents(2);
        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            if (testCase == 5)
                faculty.printFirstNStudents(10);
            else if (testCase == 6)
                faculty.printFirstNStudents(3);
            else
                faculty.printFirstNStudents(20);
        } else if (testCase == 8 || testCase == 9) {
            System.out.println("TESTING DETAILED REPORT");
            Faculty faculty = new Faculty();
            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
            int grade = 6;
            int counterCounter = 1;
            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
                for (int j = 1; j < 3; j++) {
                    try {
                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
                    } catch (OperationNotAllowedException e) {
                        e.printStackTrace();
                    }
                    grade++;
                    if (grade == 10)
                        grade = 5;
                    ++counterCounter;
                }
            }
            System.out.println(faculty.getDetailedReportForStudent("student1"));
        } else if (testCase==10) {
            System.out.println("TESTING PRINT COURSES");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            faculty.printCourses();
        } else if (testCase==11) {
            System.out.println("INTEGRATION TEST");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }

            }

            for (int i=11;i<15;i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= 3; k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("DETAILED REPORT FOR STUDENT");
            System.out.println(faculty.getDetailedReportForStudent("student2"));
            try {
                System.out.println(faculty.getDetailedReportForStudent("student11"));
                System.out.println("The graduated students should be deleted!!!");
            } catch (NullPointerException e) {
                System.out.println("The graduated students are really deleted");
            }
            System.out.println("FIRST N STUDENTS");
            faculty.printFirstNStudents(10);
            System.out.println("COURSES");
            faculty.printCourses();
        }
    }
}
