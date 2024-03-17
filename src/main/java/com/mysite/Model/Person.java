package com.mysite.Model;

import java.io.Serializable;

public record Person(String firstName, String lastName) implements Serializable {

    public static Person fromString(String personString) {
        String[] parts = personString.split(",");
        return new Person(parts[0].trim(), parts[1].trim());
    }
    @Override
    public String toString() {
        return firstName + ", " + lastName;
    }
}

