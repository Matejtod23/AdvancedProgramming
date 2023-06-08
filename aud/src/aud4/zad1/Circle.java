package aud4.zad1;

public class Circle implements Drawable<Circle> {

    private Integer circleRadius;

    public Circle(Integer circleRadius) {
        this.circleRadius = circleRadius;
    }

    @Override
    public String toString() {
        return circleRadius.toString();
    }

    @Override
    public Circle draw() {
        return this;
    }
}
