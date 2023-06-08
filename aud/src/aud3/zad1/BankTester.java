package aud3.zad1;

public class BankTester {
    public static void main(String[] args) {
        Bank bank= new Bank("FINKI");
        System.out.println(bank);

        Account a1 = new InterestCheckingAccount("Matej", 1000);
        Account a2 = new PlatinumCheckingAccount("Oli", 10000);
        Account a3 = new NonInterestCheckingAccount("Jovan", 500);

        bank.addAccount(a1);
        bank.addAccount(a2);
        bank.addAccount(a3);
        System.out.println(bank);
    }
}
