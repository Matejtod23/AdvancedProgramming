import java.util.*;

class InvalidExtraTypeException extends Exception{
    public InvalidExtraTypeException(String message) {
        super(message);
    }
}
class InvalidPizzaTypeException extends Exception{
    public InvalidPizzaTypeException(String message) {
        super(message);
    }
}

class ItemOutOfStockException extends Exception{
    public ItemOutOfStockException(String message) {
        super(message);
    }
}

class EmptyOrder extends Exception{
    public EmptyOrder(String message) {
        super(message);
    }
}

class OrderLockedException extends Exception{
    public OrderLockedException(String message) {
        System.out.print(message);
    }
}
interface Item{
    int getPrice();
    String getType();
}

class ExtraItem implements Item{
    String Etype;
    int price;

    ExtraItem(String type) throws InvalidExtraTypeException {
        if (type.equals("Ketchup") || type.equals("Coke")){
            this.Etype = type;
            setPrice();
        }else {
            throw new InvalidExtraTypeException(String.format(""));
        }

    }

    public void setPrice() {
        switch (this.Etype) {
            case "Ketchup": this.price = 3; break;
            case "Coke": this.price = 5; break;
            default: this.price = 0;
        }
    }
    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getType() {
        return Etype;
    }
}

class PizzaItem implements Item{

    String Ptype;
    int price;
    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if (type.equals("Standard") || type.equals("Pepperoni") || type.equals("Vegetarian")) {
            this.Ptype = type;
            setPrice();
        }else {
            throw new InvalidPizzaTypeException(String.format("Hello"));
        }
    }

    public void setPrice() {
        switch (this.Ptype) {
            case "Standard": this.price = 10; break;
            case "Pepperoni": this.price = 12; break;
            case "Vegetarian": this.price = 8; break;
            default: this.price = 0;
        }
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public String getType() {
        return Ptype;
    }
}

class Order{

    Item[] items;
    int[] counts;
    boolean isLocked;
    public Order() {
        items = new Item[0];
        counts = new int[0];
        isLocked = false;
    }


    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException {
        if (this.isLocked){
            throw new OrderLockedException("");
        }
        if (count > 10){
            throw new ItemOutOfStockException("");
        }
        if (alreadyInOrder(item) == -1){
            Item[] tmpI = new Item[items.length + 1];
            int[] tmpC = new int[counts.length + 1];
            for (int i = 0; i < items.length; i++){
                tmpC[i] = counts[i];
                tmpI[i] = items[i];
            }
            tmpI[items.length] = item;
            tmpC[counts.length] = count;
            items = tmpI;
            counts = tmpC;
        }
        else {
            int index = alreadyInOrder(item);
            counts[index] = count;
        }
    }

    public int alreadyInOrder(Item item){
        for (int i = 0; i < items.length; i++){
            if (items[i].getType().equals(item.getType())){
                return i;
            }
        }
        return -1;
    }


    public int getPrice() {
        int sum = 0;
        for (int i = 0; i < items.length; i++){
            sum+=items[i].getPrice()*counts[i];
        }
        return sum;
    }

    public void displayOrder() {
        for (int i = 0; i < items.length; i++){
            System.out.printf("%3d.%-15sx%2d%5d$\n", i+1, items[i].getType(), counts[i], items[i].getPrice()*counts[i]);
        }
        System.out.printf("%-22s%5d$\n", "Total:",getPrice());
    }

    public void removeItem(int idx) throws OrderLockedException {
        if (this.isLocked){
            throw new OrderLockedException("");
        }
        Item[] tmpI = new Item[items.length-1];
        int[] tmpC = new int[counts.length-1];
        int c = 0;
        for (int i = 0; i < idx; i++){
            tmpI[c] = items[c];
            tmpC[c] = counts[c];
            c++;
        }
        for (int i = idx + 1; i < items.length; i++){
            tmpI[c] = items[i];
            tmpC[c] = counts[i];
            c++;
        }
        items = tmpI;
        counts = tmpC;
    }

    public void lock() throws EmptyOrder {
        if (items.length > 0){
            this.isLocked = true;
        }else {
            throw new EmptyOrder("");
        }
    }
}

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}