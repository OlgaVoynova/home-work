package com.sbrf.reboot.service;

import com.sbrf.reboot.account.entity.Account;
import com.sbrf.reboot.account.repository.AccountRepository;
import com.sbrf.reboot.account.repository.AccountRepositoryImpl;
import com.sbrf.reboot.account.service.AccountService;
import com.sbrf.reboot.account.service.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

class AccountServiceTestMock {

    @Mock

    AccountRepository accountRepositoryImpl;

    AccountService accountService;

    private final static String ACCOUNT_ID = "ACC1234NUM";
    private final static Integer CLIENT_ID = 1;

    Account account;
    HashSet<Account> accounts;

    @BeforeEach
    void setUp() {
        accountRepositoryImpl = Mockito.mock(AccountRepositoryImpl.class);
        accountService = new AccountServiceImpl(accountRepositoryImpl);
        account = Account.createAccountWithoutInfo(ACCOUNT_ID,CLIENT_ID);
        accounts = new HashSet<>();
        accounts.add(account);
    }

    @Test
    void bookExist() {
        when(accountRepositoryImpl.getAllAccountsByClientId(CLIENT_ID)).thenReturn(accounts);
        assertTrue(accountService.isAccountExist(CLIENT_ID, account));
    }

    @Test
    void bookNotExist() {
        when(accountRepositoryImpl.getAllAccountsByClientId(CLIENT_ID)).thenReturn(accounts);
        assertFalse(accountService.isAccountExist(CLIENT_ID, Account.createAccountWithoutInfo("NEW_RANDOM_ACCOUNT",0)));
    }

    @Test
    void testCountEmptyAccounts() {
        when((accountRepositoryImpl.getAllAccountsByClientId(null))).thenReturn(accounts);
        assertEquals(accounts.size(),accountService.countEmptyAccounts());
    }

}