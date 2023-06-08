package vleznaIzad1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidIdException extends Exception{
    public InvalidIdException(String message){
        super(message);
    }
}

abstract class Customer{
     String id;
     double min;
     int SMSs;
     double GBs;


    public Customer(String id, double min, int SMSs, double GBs) {
        this.id = id;
        this.min = min;
        this.SMSs = SMSs;
        this.GBs = GBs;
    }


    abstract double totalPrice();
    abstract double commision();
}

class SCustomer extends Customer{

    static double BASE_PRICE_S = 500.0;
    static double FREE_MINUTES_S = 100.0;
    static double FREE_SMS_S = 50.0;
    static double FREE_INTERNET_S = 5.0;

    static double PRICE_PER_MINUTE = 5.0;
    static double PRICE_PER_SMS = 6.0;
    static double PRICE_PER_GB = 25.0;


    public SCustomer(String id, double min, int SMSs, double GBs) {
        super(id, min, SMSs, GBs);
    }


    @Override
    double totalPrice() {
        double total = BASE_PRICE_S;
        if (min > FREE_MINUTES_S){
            total += (PRICE_PER_MINUTE*(min-FREE_MINUTES_S));
        }
        if (SMSs > FREE_SMS_S){
            total += (PRICE_PER_MINUTE*(SMSs-FREE_SMS_S));
        }
        if (GBs > FREE_INTERNET_S){
            total += (PRICE_PER_GB*(min-FREE_INTERNET_S));
        }
        return total;
    }

    @Override
    double commision() {
        return totalPrice() * 0.07;
    }
}

class MCustomer extends Customer{

    static double BASE_PRICE_S = 750.0;
    static double FREE_MINUTES_S = 150.0;
    static double FREE_SMS_S = 60.0;
    static double FREE_INTERNET_S = 10.0;

    static double PRICE_PER_MINUTE = 4.0;
    static double PRICE_PER_SMS = 4.0;
    static double PRICE_PER_GB = 20.0;


    public MCustomer(String id, double min, int SMSs, double GBs) {
        super(id, min, SMSs, GBs);
    }

    @Override
    double totalPrice() {
        double total = BASE_PRICE_S;
        if (min > FREE_MINUTES_S){
            total += (PRICE_PER_MINUTE*(min-FREE_MINUTES_S));
        }
        if (SMSs > FREE_SMS_S){
            total += (PRICE_PER_MINUTE*(SMSs-FREE_SMS_S));
        }
        if (GBs > FREE_INTERNET_S){
            total += (PRICE_PER_GB*(min-FREE_INTERNET_S));
        }
        return total;
    }

    @Override
    double commision() {
        return totalPrice() * 0.04;
    }
}

class SalesRep implements Comparable<SalesRep>{
    String id;
    List<Customer> customers;

    public SalesRep(String id, List<Customer> customers) {
        this.id = id;
        this.customers = customers;
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics stat = customers.stream().
                mapToDouble(customers -> customers.totalPrice())
                .summaryStatistics();
        return String.format("%s Count: %d Min: %.2f Average: %.2f Max: %.2f Commision: %.2f",
                id,
                stat.getCount(),
                stat.getMin(),
                stat.getAverage(),
                stat.getMax(),
                totalCommision());
    }

    private static boolean isValid(String id) throws InvalidIdException {
        if (id.length()!=3){
            return false;
        }
        for (int i = 0; i < 3; i++){
            if (Character.isDigit(id.charAt(i)))
                return false;
        }
        return true;
    }
    public static SalesRep createSalesRep(String line) throws InvalidIdException {


        String[] parts = line.split("\\s+");
        String id = parts[0];
        if (!isValid(id)){
            throw new InvalidIdException(String.format("%d is not a valid salesrep id", id));
        }
        List<Customer> customers = new ArrayList<>();
        for (int i = 1; i < parts.length; i+=5){
            String costumerId = parts[i];
            String type = parts[i+1];
            double minutes = Double.parseDouble(parts[i+2]);
            int sms = Integer.parseInt(parts[i+3]);
            double gbs = Double.parseDouble(parts[i+4]);
            if (type.equals('M')){
                customers.add(new MCustomer(costumerId, minutes,sms,gbs));
            }else {
                customers.add(new SCustomer(costumerId, minutes,sms,gbs));
            }
        }
        return new SalesRep(id, customers);


    }

    private double totalCommision(){
        return customers.stream().mapToDouble(Customer::commision).sum();
    }

    @Override
    public int compareTo(SalesRep o) {
        return Double.compare(this.totalCommision(), o.totalCommision());
    }
}


class MobileOperator{
    List<SalesRep> salesReps;
    public MobileOperator() {
    }

    public void readSalesRepData(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        salesReps = bufferedReader.lines()
                .map(line -> {
                    try {
                        return SalesRep.createSalesRep(line);
                    } catch (InvalidIdException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        bufferedReader.close();
    }



    public void printSalesReport(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);

        salesReps.stream().sorted(Comparator.reverseOrder())
                .forEach(salesRep -> pw.println(salesRep));
        pw.flush();
    }
}
//475 4642771 M 248 90 14.94 2281930 S 139 48 6.19 4949003 M 189 100 11.90 5064198 M 159 78 9.32
public class MobileOperatorTest {
    public static void main(String[] args) {
        MobileOperator mobileOperator = new MobileOperator();
        System.out.println("---- READING OF THE SALES REPORTS ----");
        try {
            mobileOperator.readSalesRepData(System.in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("---- PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES ----");
        mobileOperator.printSalesReport(System.out);
    }
}

