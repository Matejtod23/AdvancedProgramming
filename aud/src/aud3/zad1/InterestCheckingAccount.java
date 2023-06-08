package aud3.zad1;

public class InterestCheckingAccount extends Account implements InterestBearingAccount{

    static double INTEREST_RATE = 0.03;

    public InterestCheckingAccount(String name, double balance) {
        super(name, balance);
    }

    public InterestCheckingAccount(String name) {
        super(name);
    }

    @Override
    boolean canHaveInterest() {
        return true;
    }

    @Override
    public String toString() {
        return "InterestCheckingAccount{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", balance=" + balance +
                '}';
    }

    @Override
    public void addInterest() {
        this.balance+=(this.balance*INTEREST_RATE);
    }
}
