package com.mysite.Model.contact;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
@Getter
@Setter
public class Contact implements Serializable {
    private String contactName;
    private final ArrayList<PhoneNumber> numbers;
    private String emailAddress;
    private final ArrayList<Address> addresses;

    public Contact() {
        this.numbers = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

    public Contact(String contactName) {
        this.contactName = contactName;
        this.numbers = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                %s %n
                Email address: %s%n
                Phone numbers:%n
                """.formatted(contactName, emailAddress));
        numbers.forEach(number -> sb.append(number.toString()).append("\n"));
        sb.append("Addresses%n".formatted());
        addresses.forEach(address -> sb.append(address.toString()).append("\n"));

        return sb.toString();
    }
}