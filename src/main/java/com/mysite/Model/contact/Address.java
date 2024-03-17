package com.mysite.Model.contact;

import com.mysite.util.LookupUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

enum AddressType {
    MAIN, SECOND, OFFICE, DELIVERY, OTHER;
    static public AddressType lookup(String id) {
        return LookupUtil.lookup(AddressType.class, id);
    }
}
@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private AddressType addressType;
    private String country;
    private String city;
    private String address;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    private Contact contact;

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Address() {
    }

    public Address(String country, String city, String address, String  addressType) {
        this.addressType = AddressType.lookup(addressType);
        this.country = country;
        this.city = city;
        this.address = address;
    }

}