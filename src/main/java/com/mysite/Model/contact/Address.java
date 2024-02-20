package com.mysite.Model.contact;

import com.mysite.util.LookupUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

enum AddressType {
    MAIN, SECOND, OFFICE, DELIVERY, OTHER;
    static public AddressType lookup(String id) {
        return LookupUtil.lookup(AddressType.class, id);
    }
}
@Getter
@Setter
@ToString
public class Address implements Serializable {
    private AddressType addressType;
    private String country;
    private String city;
    private String address;

    public Address() {
    }

    public Address(String country, String city, String address, String  addressType) {
        this.addressType = AddressType.lookup(addressType);
        this.country = country;
        this.city = city;
        this.address = address;
    }

}