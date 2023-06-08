package vleznazad4;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;


class IrregularCanvasException extends Exception{
    public IrregularCanvasException(String id) {
        super(String.format("Canvas %s has a shape with area larger than %.2f",id,(double)ShapesApplication.maxArea));
    }
}

abstract class Shape{
    int side;

    public Shape(int side) {
        this.side = side;
    }

    abstract double calculateArea();

}

class Square extends Shape{
    public Square(int side) {
        super(side);
    }

    public int getSide() {
        return side;
    }

    public int Parametar(){
        return 4*side;
    }

    public double calculateArea(){
        return side*side;
    }


}

class Circle extends Shape{
    public Circle(int side) {
        super(side);
    }

    public double calculateArea(){
        return Math.PI*Math.pow(side,2);
    }


}

class Canvas implements Comparable<Canvas>{
    private String id;
    List<Shape> shapes;

    public Canvas(String id,List<Shape> shapes) {
        this.id = id;
        this.shapes = shapes;
    }


    public static Canvas createCanvas(String line) throws IrregularCanvasException {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Shape> shapes1 = new ArrayList<>();
        for (int i = 1; i< parts.length;i+=2){
            if (parts[i].equals("S")){
                if (Math.pow(Integer.parseInt(parts[i+1]), 2) > ShapesApplication.maxArea){
                    throw new IrregularCanvasException(id);
                }
                shapes1.add(new Square(Integer.parseInt(parts[i+1])));
            }else {
                if (Math.PI*Math.pow(Double.parseDouble(parts[i+1]),2) > ShapesApplication.maxArea){
                    throw new IrregularCanvasException(id);
                }
                shapes1.add(new Circle(Integer.parseInt(parts[i+1])));
            }

        }

        return new Canvas(id, shapes1);
    }

//    public int sumOfParametars(){
//        return squares.stream().mapToInt(s->s.Parametar()).sum();
//    }

    public double sumOfAreas(){
        return shapes.stream().mapToDouble(shape -> shape.calculateArea()).sum();
    }

//    @Override
//    public int compareTo(Canvas o) {
//        return Integer.compare(this.sumOfParametars(), o.sumOfParametars());
//    }
        @Override
    public int compareTo(Canvas o) {
        return Integer.compare((int) this.sumOfAreas(), (int) o.sumOfAreas());
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics ds = shapes.stream()
                .mapToDouble(shapes -> shapes.calculateArea())
                .summaryStatistics();
        return String.format("%s %d %d %d %.2f %.2f %.2f"
                ,id
                ,shapes.size()
                ,shapes.stream().filter(shape -> shape.getClass() == Circle.class).count()
                ,shapes.stream().filter(shape -> shape.getClass() == Square.class).count()
                ,ds.getMin()
                ,ds.getMax()
                ,ds.getAverage());
    }

    public String getId() {
        return id;
    }
}

class ShapesApplication {

    List<Canvas> canvases = new ArrayList<>();
    static int maxArea;

    public ShapesApplication(List<Canvas> canvases) {
        this.canvases = canvases;
    }

    public ShapesApplication() {
    }

    public ShapesApplication(int i) {
        this.maxArea = i;
    }

    public void printLargestCanvasTo(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        pw.println(canvases.stream().max(Comparator.naturalOrder()).get());
        pw.flush();
    }

    public void readCanvases(InputStream in) {
        BufferedReader bf = new BufferedReader(new InputStreamReader(in));
        bf.lines().forEach(line -> {
                    try {
                        canvases.add( Canvas.createCanvas(line));
                    } catch (IrregularCanvasException e) {
                        System.out.println(e.getMessage());
                }});

//        return canvases.stream().mapToInt(c -> c.squares.size()).sum();
    }

    public void printCanvases(OutputStream  out) {
        PrintWriter pw = new PrintWriter(out);
        canvases.stream().sorted(Comparator.reverseOrder()).forEach(canvas -> pw.println(canvas));
        pw.flush();
    }

    public static int getMaxArea() {
        return maxArea;
    }
}

//public class Shapes1Test {
//
//    public static void main(String[] args) {
//        ShapesApplication shapesApplication = new ShapesApplication();
//
//        System.out.println("===READING SQUARES FROM INPUT STREAM===");
//        System.out.println(shapesApplication.readCanvases(System.in));
//        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
//        shapesApplication.printLargestCanvasTo(System.out);
//
//    }
//}
public class Shapes2Test {

    public static void main(String[] args) {

        ShapesApplication shapesApplication = new ShapesApplication(10000);

        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
        shapesApplication.readCanvases(System.in);

        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
        shapesApplication.printCanvases(System.out);


    }
}