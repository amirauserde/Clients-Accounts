package com.mysite.dto;

import com.mysite.Model.ClientPriority;
import com.mysite.Model.ClientStatus;
import com.mysite.Model.ClientType;
import com.mysite.Model.bankAccounts.Account;
import com.mysite.Model.contact.Contact;

import java.util.ArrayList;
import java.util.List;

public abstract class ClientDto {
    private String name;
    private Integer clientID;
    private ClientStatus status;
    private ClientPriority priority;
    private final ClientType clientType;
    Contact contact;
    private List<Account> accounts;


    public ClientDto (Integer id, String name, ClientType type, ClientPriority priority) {
        this.name = name;
        status = ClientStatus.NEW;
        this.priority = priority;
        clientType = type;
        clientID = id;
        contact = new Contact(name);
        accounts = new ArrayList<>(2);
    }

    public ClientDto(ClientType clientType) {
        this.clientType = clientType;
    }

    public Contact getContact() {
        return contact;
    }

    public void setStatus(String status) {
        this.status = ClientStatus.lookup(status.toUpperCase());;
    }

    public void setPriority(String priority) {
        ClientPriority p = ClientPriority.lookup(priority.toUpperCase());
        this.priority = p;
    }

    public int getClientID() {
        return clientID;
    }

    public String getPriority() {
        return priority.toString();
    }

    public String getStatus() {
        return status.toString();
    }

    public String getName() {
        return name;
    }


}
