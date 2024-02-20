package com.mysite.Model.contact;

import com.mysite.util.LookupUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

enum NumberType {
    HOME, OFFICE, MOBILE, FAX, OTHER;

    static public NumberType lookup(String id) {
        return LookupUtil.lookup(NumberType.class, id);
    }
}
@Getter
@Setter
@ToString
public class PhoneNumber implements Serializable {
    private NumberType numberType;
    private String number;

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
