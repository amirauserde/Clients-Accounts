package com.mysite.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mysite.Model.bankAccounts.Account;
import com.mysite.Model.contact.Contact;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
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
    @JsonIgnore
    private final int clientID;
    private ClientStatus status;
    private ClientPriority priority;
    private final ClientType type;
    Contact contact;
    private List<Account> accounts;


    public Client(ClientType type){
        this.clientID = count.getAndIncrement();
        this.type = type;
        accounts = new ArrayList<>(2);
    }

    public Client(String name, ClientType type, ClientPriority priority) {
        this.name = name;
        status = ClientStatus.NEW;
        this.priority = priority;
        this.type = type;
        clientID = count.getAndIncrement();
        contact = new Contact(name);
        accounts = new ArrayList<>(2);
    }

}
