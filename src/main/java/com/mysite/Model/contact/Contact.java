package com.mysite.Model.contact;

import com.mysite.Model.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contact")
@Getter
@Setter
public class Contact implements Serializable {
    @Id
    private Long contact_id;
    private String contactName;
    @OneToMany(mappedBy = "contact", fetch=FetchType.EAGER)
    private final List<PhoneNumber> numbers;
    private String emailAddress;
    @OneToMany(mappedBy = "contact", fetch=FetchType.LAZY)
    private final List<Address> addresses;
    @OneToOne
    @MapsId
    @JoinColumn(name = "CLIENTID")
    private Client client;

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