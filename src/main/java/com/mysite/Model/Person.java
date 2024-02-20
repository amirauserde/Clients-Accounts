package com.mysite.Model;

import java.io.Serializable;

public record Person(String firstName, String lastName) implements Serializable {
    @Override
    public String toString() {
        return firstName + ", " + lastName;
    }
}

