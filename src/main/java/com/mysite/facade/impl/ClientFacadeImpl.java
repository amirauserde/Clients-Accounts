package com.mysite.facade.impl;

import com.mysite.Model.ClientPriority;
import com.mysite.dto.ClientDto;
import com.mysite.dto.LegalClientDto;
import com.mysite.dto.RealClientDto;
import com.mysite.service.AccountManagement;
import com.mysite.service.ClientManagement;
import com.mysite.service.Validation.ClientValidationContext;
import com.mysite.service.exception.AccountNotFoundException;
import com.mysite.service.exception.ClientNotFoundException;
import com.mysite.service.exception.FileException;
import com.mysite.service.exception.ValidationException;

import java.util.List;

public class ClientFacadeImpl {

    private final ClientManagement clientManagement;
    private final AccountManagement accountManagement;
    private static final ClientFacadeImpl INSTANCE;
    private ValidationContext<ClientDto> validationContext;


    public static ClientFacadeImpl getInstance() {
        return INSTANCE;
    }

    static {
        INSTANCE = new ClientFacadeImpl();
    }

    private ClientFacadeImpl() {
        this.clientManagement = ClientManagement.getInstance();
        this.accountManagement = AccountManagement.getInstance();
        validationContext = new ClientValidationContext();
    }

    public ClientFacadeImpl(ClientManagement clientManagement) {
        validationContext = INSTANCE.validationContext;
        accountManagement = INSTANCE.accountManagement;
        this.clientManagement = clientManagement;
    }

    public int addClient(int type, String name, String secondElement, String priority, String password)
            throws ValidationException {
        if(findClient(name).size() > 0) {
            if(findClient(secondElement + ", " + name).size() > 0 ||
                    findClient(secondElement).size() > 0) {
                return -1;
            }
        }
        ClientDto newClientDto;

        if(type == 1) {
            newClientDto = new RealClientDto(null, name, secondElement,
                    ClientPriority.lookup(priority.toUpperCase()));
        } else {
            newClientDto = new LegalClientDto(null, name, secondElement,
                    ClientPriority.lookup(priority.toUpperCase()));
        }
        validationContext.validate(newClientDto);

        return clientManagement.addClient(type, name, secondElement, priority, password);
    }

    public void printClients() {
        clientManagement.printClients();
    }

    public String printClientDetails(int clientId) throws AccountNotFoundException, ClientNotFoundException {
        return clientManagement.printClientDetails(clientId) + accountsToString(clientId);
    }

    private String accountsToString(int clientId) throws AccountNotFoundException, ClientNotFoundException {
        String accountsToString = "%nAccounts:%n".formatted();
        List<Integer> accounts = clientManagement.getClientById(clientId).getAccountNos();
        for(int accNo : accounts) {
            accountsToString += "%s%n".formatted(accountManagement.getAccountByAccNo(accNo).toString());
        }
        return accountsToString;
    }

    public String activeClientBrief(int clientId) {
        return clientManagement.activeClientBrief(clientId);
    }

    public List<Integer> findClient(String searchItem) {
        return clientManagement.findClient(searchItem);
    }

    public boolean addPhoneNumber(int clientId, String number, String numberType) {
        return clientManagement.addPhoneNumber(clientId, number, numberType);
    }

    public void setEmail(int clientId, String emailAddress) throws ValidationException {
        clientManagement.setEmail(clientId, emailAddress);
    }

    public boolean addAddress(int clientId, String[] info) {
        return clientManagement.addAddress(clientId, info);
    }

    public boolean statusChange(int clientId, String status) throws ClientNotFoundException {
        return clientManagement.statusChange(clientId, status);
    }

    public boolean priorityChange(int clientId, String priority) throws ClientNotFoundException {
        return clientManagement.priorityChange(clientId, priority);
    }

    public void saveData(int fileType, String name) throws FileException {
        clientManagement.saveData(fileType, name);
    }

    public void loadData(int fileType, String name) throws FileException {
        clientManagement.loadData(fileType, name);
    }

    public void initData() {
        clientManagement.initData();
    }

    public void saveOnExit() {
        clientManagement.saveOnExit();
    }

    public void addData(String name) throws FileException {
        clientManagement.addData(name);
    }

    public boolean login(int clientID, String password) {
        return clientManagement.login(clientID, password);
    }
}
