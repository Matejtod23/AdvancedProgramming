package aud3.zad1;

abstract public class Account {
    String name;
    long id;
    static long ID_COUNTER = 1000;
    double balance;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
        id = ID_COUNTER++;//kje zeme 1000 pa se zgolemuva za 1
    }
    public Account(String name){
        this.name = name;
        this.balance = 0;
        id = ID_COUNTER++;
    }

    public double getBalance() {
        return balance;
    }
    public void deposti(double amount){
        balance+=amount;
    }
    public void withdraw(double amount) throws InsufficientAmountException {
        if (amount <= balance){
            balance -= amount;
        }else {
            throw new InsufficientAmountException(amount);
        }
    }

    abstract boolean canHaveInterest();
}
