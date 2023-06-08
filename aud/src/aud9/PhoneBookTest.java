package aud9;

import java.util.HashSet;
import java.util.Set;

class Contact{
    String name;
    String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, number);
    }

}

class DuplicateNumberException extends Exception{
    DuplicateNumberException(String number){
        super(String.format("Duplicate number %s",number));
    }
}

class PhoneBook{
    Set<String> uniquePhoneNumbers;

    PhoneBook() {
        uniquePhoneNumbers = new HashSet<>();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        if (uniquePhoneNumbers.contains(number)){
            throw new DuplicateNumberException(number);
        }else {
            uniquePhoneNumbers.add(number);
        }
    }
}

public class PhoneBookTest {
}
