package aud3.zad1;

public class InsufficientAmountException extends Exception{
    InsufficientAmountException(double amount){
        super(String.format("You don't have %.2f$ on your account", amount));
    }
}
