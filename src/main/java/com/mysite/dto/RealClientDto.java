package com.mysite.dto;

import com.mysite.Model.ClientPriority;
import com.mysite.Model.ClientType;
import com.mysite.Model.Person;
import com.mysite.Model.contact.Contact;

public class RealClientDto extends ClientDto{
    Person client;
    String lastName;

    public RealClientDto() {
        super(ClientType.REAL);
    }

    public RealClientDto(Integer id, String firstName, String lastName, ClientPriority priority) {
        super(id, lastName + ", " + firstName, ClientType.REAL, priority);
        this.lastName = lastName;
        client = new Person(firstName, lastName);
        contact = new Contact(firstName + " " + lastName + "contact details");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "%15s: %s".formatted("ID",super.getClientID()) + "\n" +
                "first name: %-10s family name: %s%n".formatted(client.firstName(), client.lastName()) +
                "Priority: %s%n".formatted(super.getPriority()) +
                super.contact.toString();
    }

    public String getLastName() {
        return lastName;
    }
}