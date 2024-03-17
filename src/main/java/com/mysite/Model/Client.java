package com.mysite.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mysite.Model.contact.Contact;
import com.mysite.util.IntArrayToStringConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Entity
@Table(name = "client")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = LegalClient.class, name = "LEGAL"),
        @JsonSubTypes.Type(value = RealClient.class, name = "REAL")
})
@Getter
@Setter
public abstract class Client implements Serializable {
    private String name;
    private static final AtomicInteger count = new AtomicInteger(1);
    @Id
    @JsonIgnore
    private final int clientID;
    @Enumerated(EnumType.STRING)
    private ClientStatus status;
    @Enumerated(EnumType.STRING)
    private ClientPriority priority;
    @Enumerated(EnumType.STRING)
    private final ClientType type;
    @OneToOne(mappedBy = "client", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    Contact contact = new Contact(name);
    @Column
    @Convert(converter = IntArrayToStringConverter.class)
    private List<Integer> accountNos;
    private String password;


    public Client(ClientType type){
        this.clientID = count.getAndIncrement();
        this.type = type;
        accountNos = new ArrayList<>(2);
    }

    public Client(String name, ClientType type, ClientPriority priority, String password) {
        this.name = name;
        status = ClientStatus.NEW;
        this.priority = priority;
        this.type = type;
        clientID = count.getAndIncrement();
        accountNos = new ArrayList<>(2);
        this.password = password;
    }

}
