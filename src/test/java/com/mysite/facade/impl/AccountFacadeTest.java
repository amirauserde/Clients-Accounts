package com.mysite.facade.impl;

import com.mysite.Model.Client;
import com.mysite.Model.RealClient;
import com.mysite.Model.bankAccounts.Account;
import com.mysite.service.AccountManagement;
import com.mysite.service.ClientManagement;
import com.mysite.service.exception.AccountNotFoundException;
import com.mysite.service.exception.ClientNotFoundException;
import com.mysite.service.exception.ValidationException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Currency;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class AccountFacadeTest {
    private AccountFacadeImpl accountFacade;
    @Mock
    private AccountManagement accountManagement;
    @Mock
    private ClientManagement<Client> clientManagement;

    @Before
    public void setup() {
        accountFacade = new AccountFacadeImpl(clientManagement, accountManagement);
    }
    @Ignore
    @Test
    public void addAccountTest() {
        Exception exception = assertThrows(ClientNotFoundException.class, () ->
                accountFacade.addAccount(Currency.getInstance("EUR"), 12));

        assertEquals("Client not found exception!", exception.getMessage());
    }

    @Test
    public void addAccount() throws ClientNotFoundException, ValidationException, AccountNotFoundException {
        when(clientManagement.getClientById(12)).thenReturn(new RealClient());

        accountFacade.addAccount(Currency.getInstance("EUR"), 12);
        verify(accountManagement).addAccount(any(Account.class));
    }
}
