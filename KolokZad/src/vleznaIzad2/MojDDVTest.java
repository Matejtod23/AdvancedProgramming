package vleznaIzad2;

import javax.lang.model.element.Element;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


enum TaxType{
    A,B,V
}

class AmountNotAllowedException extends Exception{
    public AmountNotAllowedException(int amount){
        super(String.format("Receipt with amount %d is not allowed to be scanned",amount));
    }
}

class Item{


    int price;
    TaxType tax;

    public Item(int price, TaxType tax) {
        this.price = price;
        this.tax = tax;
    }

    public Item(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public TaxType getTax() {
        return tax;
    }

    public void setTax(TaxType tax) {
        this.tax = tax;
    }
    public double getCalculatedTax(){
        if (tax.equals(TaxType.A)) return 0.18 * price;
        else if (tax.equals(TaxType.B)) return 0.05*price;
        else return 0;
    }
}

class FiskalnaSmetka{
    private long id;
    private List<Item> items;

    public FiskalnaSmetka(long id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public FiskalnaSmetka(long id){
        this.id = id;
        this.items = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public static FiskalnaSmetka createFinal(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        long id = Long.parseLong(parts[0]);
        List<Item> items = new ArrayList<>();

        Arrays.stream(parts)
                .skip(1)
                .forEach(i -> {
                    if (Character.isDigit(i.charAt(0))){
                        items.add(new Item(Integer.parseInt(i)));
                    }else {
                        items.get(items.size()-1).setTax(TaxType.valueOf(i));
                    }
                });

        if (totalAmount(items) > 30000)
            throw new AmountNotAllowedException(totalAmount(items));
        return new FiskalnaSmetka(id, items);
    }

    public static int totalAmount(List<Item> items){
        return items.stream().mapToInt(Item::getPrice).sum();
    }

    public static double totalTaxReturn(List<Item> items){
        return (items.stream().mapToDouble(Item::getCalculatedTax).sum())*0.15;
    }
    public double taxReturn(){
        return (items.stream().mapToDouble(Item::getCalculatedTax).sum())*0.15;
    }

    public static String addedChars(String value){
        String str = value;
        int charsToBeAdded = 10 - str.length();
        String blank = "";
        for (int i = 0; i < charsToBeAdded; i++){
            blank+=" ";
        }
        return blank+str;
    }

    @Override
    public String toString() {
        String id = addedChars(String.valueOf(getId()));
        String total = addedChars(String.valueOf(totalAmount(items)));
        String tax = addedChars(String.format("%.5f", totalTaxReturn(items)));
        return String.format("%s\t%s\t%s", id, total,tax);
    }
}
class MojDDV{

    List<FiskalnaSmetka> smetki;

    public MojDDV() {
        this.smetki = new ArrayList<>();
    }

    public void readRecords(InputStream in) {
        smetki = new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(l -> {
                    try {
                        return FiskalnaSmetka.createFinal(l);
                    } catch (AmountNotAllowedException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .collect(Collectors.toList());
        smetki = smetki.stream().filter(Objects::nonNull).collect(Collectors.toList());//gi trga site objekti koi se null
    }

    public void printTaxReturns(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        smetki.stream().forEach(smetka -> pw.println(smetka));
        pw.flush();
    }

    public void printStatistics(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        DoubleSummaryStatistics ds = smetki
                .stream()
                .mapToDouble(FiskalnaSmetka::taxReturn)
                .summaryStatistics();
        pw.format("min:\t%.3f\nmax:\t%.3f\nsum:\t%.3f\ncount:\t%-5d\navg:\t%.3f",
                       ds.getMin(),
                       ds.getMax(),
                       ds.getSum(),
                       ds.getCount(),
                       ds.getAverage());
        pw.flush();

    }
}

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}