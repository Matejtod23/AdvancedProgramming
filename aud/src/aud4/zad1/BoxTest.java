package aud4.zad1;

public class BoxTest {
    public static void main(String[] args) {
        Box<Circle> box = new Box<>();
        box.add(new Circle(2));
        box.add(new Circle(4));
        box.add(new Circle(5));
        box.add(new Circle(200));
        box.add(new Circle(26));


        while (!box.isEmpty()){
            System.out.println(box.draw());
        }

    }
}
