package kolok2.zad1;

import java.util.*;

class UserAlreadyExistsException extends Exception{
    UserAlreadyExistsException(String message){
        super(message);
    }
}

class User{
    String email;
    String name;
    String phone;
    int id;
    static  int ID_COUNTER = 1;

    public User(String email, String name, String phone) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        id = ID_COUNTER++;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getId() {
        return id;
    }

    public static int getIdCounter() {
        return ID_COUNTER;
    }
}
class PizzaApp{

    Map<String, User> userByEmail;
    Map<String, Float> revenueByPizza;
    //<pizza name, <email, brojpici>>
    Map<String, Map<String, Integer>> orderByPizza;

    public PizzaApp() {
        userByEmail = new HashMap<>();
        revenueByPizza = new TreeMap<>(Comparator.reverseOrder());
        orderByPizza = new TreeMap<>();// za da sporedi leksikogravski
    }

    public void registerUser(String email, String name, String phone) throws UserAlreadyExistsException {
        if (userByEmail.containsKey(email)){
            throw new UserAlreadyExistsException(String.format("User with email %s already exists!", email));
        }
        User user = new User(email, name, phone);
        userByEmail.put(email, user);
    }

    public void makeOrder(String email, String pizzaName, float price) {
        revenueByPizza.putIfAbsent(pizzaName, 0.0f);
        revenueByPizza.computeIfPresent(pizzaName, (k,v) -> {
            v += price;
            return v;
        });

        orderByPizza.putIfAbsent(pizzaName, new HashMap<>());
        orderByPizza.get(pizzaName).putIfAbsent(email, 0);
        orderByPizza.get(pizzaName).computeIfPresent(email, (k, v) -> ++v);//se pristapuva picata spored ime
        //potoa se bara dali go ima toj mail ako go ima se zgolemuva value(broj pici) za 1

    }

    public void printRevenueByPizza() {
//        revenueByPizza.forEach((k,v) -> System.out.println(String.format("%s %.2f", k, v)));
        revenueByPizza.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(String.format("%s, %.2f", entry.getKey(), entry.getValue())));
    }

    public void printMostFrequentUserForPizza() {
        for (Map.Entry<String, Map<String, Integer>> entry : orderByPizza.entrySet()) {
            System.out.println(String.format("Pizza: %s",entry.getKey()));
            Map<String, Integer> frequencyByEmail = entry.getValue();

            int max = frequencyByEmail.values().stream().mapToInt(i -> i).max().getAsInt();

            System.out.println("ID email frequency");
            frequencyByEmail.keySet().stream()
                    .filter(email -> frequencyByEmail.get(email) == max)
                    .map(email -> userByEmail.get(email))
                    .sorted(Comparator.comparing(User::getId))
                    .forEach(user -> System.out.println(String.format("%d %s %d", user.getId(), user.getEmail()
                    ,max)));
        }
    }
}

public class PizzaAppTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PizzaApp pizzaApp = new PizzaApp();
        
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            
            String method = parts[0];
            
            if (method.equalsIgnoreCase("registerUser")){
                String email = parts[1];
                String name = parts[2];
                String phone = parts[3];
                try {
                    pizzaApp.registerUser(email, name, phone);
                } catch (UserAlreadyExistsException e) {
                    System.out.println(e.getMessage());
                }
            } else if (method.equalsIgnoreCase("makeOrder")) {
                String email = parts[1];
                String pizzaName = parts[2];
                float price = Float.parseFloat(parts[3]);
                pizzaApp.makeOrder(email, pizzaName, price);
            } else if (method.equalsIgnoreCase("printRevenueByPizza")) {
                System.out.println("Print revenue by pizza");
                pizzaApp.printRevenueByPizza();
            }else {
                System.out.println("Print most frequent user(s) by pizza");

                pizzaApp.printMostFrequentUserForPizza();
            }
        }
    }
}
