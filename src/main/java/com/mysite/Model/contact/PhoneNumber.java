package com.mysite.Model.contact;

import com.mysite.util.LookupUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

enum NumberType {
    HOME, OFFICE, MOBILE, FAX, OTHER;

    static public NumberType lookup(String id) {
        return LookupUtil.lookup(NumberType.class, id);
    }
}
@Entity
@Table(name = "phone_number")
@Getter
@Setter
@ToString
public class PhoneNumber implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    private NumberType numberType;
    private String number;
    @ManyToOne(fetch = FetchType.LAZY) // Adjust fetching strategy if needed
    @JoinColumn(name = "contact_id")
    private Contact contact;

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public PhoneNumber() {
    }

    public PhoneNumber(String number, String  numberType) {
        this.numberType = NumberType.lookup(numberType.toUpperCase());
        this.number = numberFormat(number);
    }

    private String numberFormat(String number) {
        return number.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
    }

}
