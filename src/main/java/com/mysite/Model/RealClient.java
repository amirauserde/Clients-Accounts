package com.mysite.Model;

import com.mysite.util.PersonConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "real_client")
@Getter
@Setter
public class RealClient extends Client implements Serializable {
    @Column
    @Convert(converter = PersonConverter.class)
    Person person;

    public RealClient() {
        super(ClientType.REAL);
    }

    public RealClient(String firstName, String lastName, ClientPriority priority, String password) {
        super(lastName + ", " + firstName, ClientType.REAL, priority, password);
        person = new Person(firstName, lastName);
    }

    @Override
    public String toString() {
        return super.getType() + "%15s: %s".formatted("ID",super.getClientID()) + "\n" +
                "first name: %-10s family name: %s%n".formatted(person.firstName(), person.lastName()) +
                "Priority: %s%n".formatted(super.getPriority()) +
                super.contact.toString();
    }
}