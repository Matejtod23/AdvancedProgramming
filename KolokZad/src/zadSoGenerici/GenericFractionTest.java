package zadSoGenerici;

import java.util.Scanner;

class ZeroDenominatorException extends Exception{
    public ZeroDenominatorException(String message) {
        super(message);
    }
}

class GenericFraction<T extends Number, U extends Number>{
    T numerator;
    U denominaotr;

    public GenericFraction(T numerator, U denominaotr) throws ZeroDenominatorException {
        if (denominaotr.equals(0)){
            throw new ZeroDenominatorException(String.format("Denominator cannot be zero"));
        }
        this.numerator = numerator;
        this.denominaotr = denominaotr;
    }

    GenericFraction<Double, Double> add(GenericFraction<? extends Number,? extends Number> gf) throws ZeroDenominatorException {
        if (gf.denominaotr.doubleValue() == denominaotr.doubleValue()){
            double n = numerator.doubleValue() + gf.numerator.doubleValue();
            double d = denominaotr.doubleValue();
            return new GenericFraction<>(n,d);
        }else {
            double gcd = GCD(denominaotr.intValue(), gf.denominaotr.intValue());
            double d = (denominaotr.doubleValue() * gf.denominaotr.doubleValue())/gcd;
            double n = (numerator.doubleValue()*(d/denominaotr.doubleValue()) + (gf.numerator.doubleValue()*(d/gf.denominaotr.doubleValue())));
            return new GenericFraction<>(n,d);
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f / %.2f", numerator, denominaotr);
    }

    public double toDouble() {
        return numerator.doubleValue() / denominaotr.doubleValue();
    }

    public int GCD(int a, int b) {
        if (b == 0) return a;
        return GCD(b,a%b);
    }

}

public class GenericFractionTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double n1 = scanner.nextDouble();
        double d1 = scanner.nextDouble();
        float n2 = scanner.nextFloat();
        float d2 = scanner.nextFloat();
        int n3 = scanner.nextInt();
        int d3 = scanner.nextInt();
        try {
            GenericFraction<Double, Double> gfDouble = new GenericFraction<Double, Double>(n1, d1);
            GenericFraction<Float, Float> gfFloat = new GenericFraction<Float, Float>(n2, d2);
            GenericFraction<Integer, Integer> gfInt = new GenericFraction<Integer, Integer>(n3, d3);
            System.out.printf("%.2f\n", gfDouble.toDouble());
            System.out.println(gfDouble.add(gfFloat));
            System.out.println(gfInt.add(gfFloat));
            System.out.println(gfDouble.add(gfInt));
            gfInt = new GenericFraction<Integer, Integer>(n3, 0);
        } catch(ZeroDenominatorException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

}

// вашиот код овде
