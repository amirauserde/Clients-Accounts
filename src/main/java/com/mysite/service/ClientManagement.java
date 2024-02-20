package com.mysite.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysite.Model.*;
import com.mysite.Model.bankAccounts.Account;
import com.mysite.Model.bankAccounts.AccountType;
import com.mysite.Model.contact.Address;
import com.mysite.Model.contact.Contact;
import com.mysite.Model.contact.PhoneNumber;
import com.mysite.service.exception.FileException;
import com.mysite.util.MapperWrapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class ClientManagement<T extends Client> {

    private ArrayList<T> clients;
    private static ClientManagement INSTANCE;

    private final String fileName = "clientData";

    public static ClientManagement getInstance() {
        if(INSTANCE == null) {
            synchronized (ClientManagement.class) {
                if(INSTANCE == null){
                    INSTANCE = new ClientManagement();
                }
            }
        }
        return INSTANCE;
    }

    private final ObjectMapper objectMapper;

    private ClientManagement() {
        this.clients = new ArrayList<>();
        objectMapper = MapperWrapper.getInstance();
    }

    public int addClient(T client) {
        clients.add(client);
        return client.getClientID();
    }

    public int addClient(int type, String name, String secondElement, String priority) {
        if(findClient(name).size() > 0) {
            if(findClient(secondElement + ", " + name).size() > 0 ||
                    findClient(secondElement).size() > 0) {
                return -1;
            }
        }
        Client newClient;

        if(type == 1) {
            newClient = new RealClient(name, secondElement,
                    ClientPriority.lookup(priority.toUpperCase()));
        } else {
            newClient = new LegalClient(name, secondElement,
                    ClientPriority.lookup(priority.toUpperCase()));
        }

        return addClient((T) newClient);
    }


    public boolean priorityChange(int clientId, String priority) {
        if(ClientPriority.contains(priority)) {
            Client client = clients.stream()
                    .filter(c -> c.getClientID() == clientId)
                    .findFirst()
                    .orElse(null);
            if (client == null) {
                return false;
            }
            client.setPriority(ClientPriority.lookup(priority.toUpperCase()));
            return true;
        }
        return false;
    }

    public boolean statusChange(int clientId, String status) {
        if(ClientStatus.contains(status)) {
            Client client = clients.stream()
                    .filter(c -> c.getClientID() == clientId)
                    .findFirst()
                    .orElse(null);
            if (client == null) {
                return false;
            }
            client.setStatus(ClientStatus.lookup(status.toUpperCase()));
            return true;
        }
        return false;
    }

    public boolean addPhoneNumber(int clientId, String number, String numberType) {
        Contact contact = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .map(Client::getContact)
                .findFirst()
                .orElse(null);
        if (contact == null) {
            return false;
        }

        if(contact.getNumbers().stream().anyMatch(s ->
                s.getNumber().equals(number.replaceAll("[()-]", "")))) {
            return false;
        }
        if(!(number.length() == 10 && number.matches("\\d{10}"))) {
            throw new ArithmeticException("Please Enter the number in correct format!");
        }
        contact.getNumbers().add(new PhoneNumber(number, numberType));
        return true;
    }

    public void addAccount(int clientId, Account account) {
        Client client = clients.stream().filter(c -> c.getClientID() == clientId).findFirst().orElse(null);
        client.getAccounts().add(account);
    }

    public boolean checkAccounts(int clientId, int type) {
        Client client = clients.stream().filter(c -> c.getClientID() == clientId).findFirst().orElse(null);
        return (client.getAccounts().stream().noneMatch(ac -> ac.getType().equals(AccountType.fromValue(type))));
    }

    public boolean addAddress(int clientId, String[] info) {
        Contact contact = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .map(Client::getContact)
                .findFirst()
                .orElse(null);
        if (contact == null) {
            return false;
        }
        Address newAddress = new Address(info[0], info[1], info[2], info[3].toUpperCase());
        if(contact.getAddresses().contains(newAddress)) {
            return false;
        }
        contact.getAddresses().add(newAddress);
        return true;
    }

    public void setEmail(int clientId, String emailAddress) {
        Contact contact = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .map(Client::getContact)
                .findFirst()
                .orElse(null);
        if (contact == null) {
            return;
        }
        contact.setEmailAddress(emailAddress);
    }


    public List<Integer> findClient(String searchItem) {
        List<Integer> results;
        results = clients.stream().filter(client -> client.getName().toUpperCase()
                        .contains(searchItem.toUpperCase()) || String.valueOf(client.getClientID()).equals(searchItem))
                .map(Client::getClientID).collect(Collectors.toList());
        if(results.isEmpty()) {
            results = clients.parallelStream().
                    filter(client -> String.valueOf(client.getPriority()).equalsIgnoreCase(searchItem) ||
                            String.valueOf(client.getStatus()).equalsIgnoreCase(searchItem))
                    .map(Client::getClientID).collect(Collectors.toList());
        }
        return results;
    }

    public void printClients() {
        if(clients.isEmpty()) {
            System.out.println("There is still no client :(");
            return;
        }
        clients.stream().filter(not(s -> s.getStatus().toString().equalsIgnoreCase("Past")))
                .forEach(s -> System.out.println(activeClientBrief(s.getClientID())));
    }

    public String activeClientBrief(int clientId) {
        Client client = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .filter(not(c -> c.getStatus().toString().equalsIgnoreCase("Past")))
                .findFirst()
                .orElse(null);
        if (client == null) {
            return "No client found";
        }
        return "%d) %-15s%-10s%s".formatted(clientId,
                client.getName(), client.getPriority(), client.getStatus());
    }

    public String printClientDetails(int clientId) {
        Client client = clients.stream()
                .filter(c -> c.getClientID() == clientId)
                .findFirst()
                .orElse(null);
        if (client == null) {
            return "No client found";
        }
        return client.toString();
    }

    public void saveData(int type, String name) throws FileException {
        switch (type) {
            case 1 -> saveSerialized(name);
            default -> saveJason(name);
        }
    }

    private void saveSerialized(String name) throws FileException {
        try {
            File file = new File(name + ".crm");
            if (!file.exists()) {
                file.createNewFile();
            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(file);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(clients);
            }
        } catch (IOException e) {
            throw new FileException();
        }
    }

    private void saveJason(String name) throws FileException {
        try {
            File file = new File(name + ".jsn");
            if (!file.exists()) {
                file.createNewFile();
            }
            objectMapper.writeValue(file, clients);

        } catch (IOException e) {
            throw new FileException();
        }
    }

    public void loadData(int type, String name) throws FileException {
        switch (type) {
            case 1 -> loadSerialized(name);
            default -> loadJason(name);
        }
    }

    private void loadSerialized(String name) throws FileException {
        try {
            FileInputStream fileInputStream = new FileInputStream(name + ".crm");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            clients = (ArrayList<T>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new FileException();
        }
    }

    private void loadJason(String name) throws FileException {
        try {
            clients = (ArrayList<T>) objectMapper.readValue(new File(name + ".jsn"),
                    new TypeReference<ArrayList<T>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException();
        }
    }

    public void initData() {
        try {
            loadJason(fileName);
        } catch (FileException ignored) {

        }
    }

    public void saveOnExit() {
        try {
            saveJason(fileName);
        } catch (FileException ignored) {

        }
    }

    public void addData(String name) throws FileException {
        try {
            ArrayList<T> newClients = (ArrayList<T>) objectMapper.readValue(new File(name + ".jsn"),
                    new TypeReference<ArrayList<T>>() {});
            clients.addAll(newClients);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileException();
        }
    }
}
