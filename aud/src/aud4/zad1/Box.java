package aud4.zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Box<T extends Drawable<T>> {

    private List<T> elements;
    private static Random random = new Random();

    public Box() {
        elements = new ArrayList<>();
    }

    public void add(T element){
        elements.add(element);
    }

    public boolean isEmpty(){
        return elements.size() == 0;
    }

    public T draw(){
        if (isEmpty()) return null;
        T randomElement = elements.remove(random.nextInt(elements.size()));
        randomElement.draw();
        return randomElement;
    }

}
