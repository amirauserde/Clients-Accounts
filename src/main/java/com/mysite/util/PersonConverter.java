package com.mysite.util;

import com.mysite.Model.Person;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PersonConverter implements AttributeConverter<Person, String> {

    @Override
    public String convertToDatabaseColumn(Person person) {
        return person.toString();
    }

    @Override
    public Person convertToEntityAttribute(String dbData) {
        return Person.fromString(dbData);
    }
}
