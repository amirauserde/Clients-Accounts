package com.mysite.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
@Entity
@Table(name = "legal_client")
@Getter
@Setter
public class LegalClient extends Client implements Serializable {
    String companyName;
    String nationalCode;

    public LegalClient() {
        super(ClientType.LEGAL);
    }

    public LegalClient(String name, String nationalCode, ClientPriority priority, String password) {
        super(name, ClientType.LEGAL, priority, password);
        this.companyName = name;
        this.nationalCode = nationalCode;
    }

    @Override
    public String toString() {
        return super.getType() + "%15s: %s".formatted("ID",super.getClientID()) + "\n" +
                "name: %-10s National code: %s%n".formatted(companyName, nationalCode) +
                "Priority: %s%n".formatted(super.getPriority()) + "\n" +
                super.contact.toString();
    }
}