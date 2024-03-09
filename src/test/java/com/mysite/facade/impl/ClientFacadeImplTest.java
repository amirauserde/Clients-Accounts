package com.mysite.facade.impl;

import com.mysite.Model.Client;
import com.mysite.Model.ClientPriority;
import com.mysite.Model.RealClient;
import com.mysite.service.ClientManagement;
import com.mysite.service.exception.ValidationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ClientFacadeImplTest {
    private ClientFacadeImpl clientFacade;
    @Mock
    private ClientManagement<Client> clientManagement;

    @Before
    public void setup() {
        clientFacade = new ClientFacadeImpl(clientManagement);
    }
    @Ignore
    @Test
    public void addClientInvalidEmailTest() throws ParseException, ValidationException {
        //RealClient client = new RealClient("test", "test1", ClientPriority.CRITICAL, "password");
        //clientManagement.addClient(client);
        clientFacade.addClient(1, "test", "test1", "high", "password");
        Exception exception = assertThrows(ValidationException.class, () ->
                clientFacade.setEmail(1, "test"));
        assertEquals("Please enter Email in correct format.", exception.getMessage());
    }
    @Ignore
    @Test
    public void addClientInvalidPhoneTest() throws ParseException {
        Client client = new RealClient("test", "test1", ClientPriority.CRITICAL, "password");
        clientManagement.addClient(client);
        Exception exception = assertThrows(ArithmeticException.class, () ->
                clientFacade.addPhoneNumber(client.getClientID(), "qwertgfdsh", "home"));
        assertEquals(exception.getMessage(), "Please Enter the number in correct format!");
    }

    @Test
    public void addClientInvalidNameTest() throws ParseException {
        Exception exception = assertThrows(ValidationException.class, () ->
                clientFacade.addClient(1, "", "test1", "high", "password1"));
        assertEquals(exception.getMessage(), "Name must not be empty or null");
    }

    @Test
    public void addClientInvalidPriorityTest() throws ParseException {
        Exception exception = assertThrows(RuntimeException.class, () ->
                clientFacade.addClient(1, "test", "test1", "test", "password1"));
        assertEquals(exception.getMessage(), "Invalid value for enum ClientPriority: TEST");
    }

    @Test
    public void addClientTest() throws ParseException, ValidationException {
        clientFacade.addClient(1, "test", "test1", "low", "password1");

        verify(clientManagement).
                addClient(1, "test", "test1", "low", "password1");
    }
}
