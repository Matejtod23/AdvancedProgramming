package aud3.intro;

interface Printable{
    void print();
}

//3 nacini za pristap na interfejs
class NumberPrintable implements Printable{

    int number;

    public NumberPrintable(int number) {
        this.number = number;
    }

    @Override
    public void print() {
        System.out.printf("Number is %d", number);
    }
}

abstract class Animals {
    String name;

    public Animals(String name) {
        this.name = name;
    }
    abstract String getSound();
}


class Cat extends Animals{

    public Cat(String name) {
        super(name);
    }

    @Override
    String getSound() {
        return "Mjauuu";
    }
}

class Dog extends Animals{

    public Dog(String name) {
        super(name);
    }

    @Override
    String getSound() {
        return "AFAF";
    }
}

public class IntroTest{
    public static void main(String[] args) {
//        Animals [] animals = new Animals[2];
//        animals[0] = new Cat("garfield");
//        animals[1] = new Dog("dzeki");
//
//        for (Animals animal: animals){
//            System.out.println(animal.getSound());
//        }
        //1. kreiranje klasa sto pristapuva do interfejsot
        Printable p1 = new NumberPrintable(5);
        p1.print();

        //2. anonimni klasi
        Printable p2 = new Printable() {
            @Override
            public void print() {
                System.out.printf("\nAnonimna klasa");
            }
        };
        p2.print();

        //3. Lambda izrazi(funkcionalno programiranje)
        //lamba izraz se koristi akko ima samo eden metod
        Printable p3 = ()-> System.out.println("Lambda expression");
        p3.print();
    }
}
