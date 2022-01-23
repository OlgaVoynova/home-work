package com.sbrf.reboot.service;

import com.sbrf.reboot.account.entity.Account;
import com.sbrf.reboot.account.repository.AccountRepository;
import com.sbrf.reboot.account.service.AccountService;
import com.sbrf.reboot.account.service.AccountServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    AccountService accountService;

    @BeforeEach
    void setUp() {
        accountRepository = Mockito.mock(AccountRepository.class);

        accountService = new AccountServiceImpl(accountRepository);
    }

    @SneakyThrows
    @Test
    void getMaxAccountBalance() {
        Account accountWithMaxBalance = Account.builder().clientId(1).id("4L").balance(new BigDecimal(150000)).build();
        HashSet<Account> accounts = new HashSet<Account>() {{
            add(Account.builder().clientId(1).id("1L").balance(BigDecimal.TEN).build());
            add(Account.builder().clientId(1).id("2L").balance(new BigDecimal(200)).build());
            add(Account.builder().clientId(1).id("3L").balance(new BigDecimal("1.65")).build());
            add(accountWithMaxBalance);
        }};

        when(accountRepository.getAllAccountsByClientId(1)).thenReturn(accounts);

        assertEquals(accountWithMaxBalance, accountService.getMaxAccountBalance(1));
    }


    @SneakyThrows
    @Test
    void getAllAccountsByDateMoreThen() {
        Account account1 = Account.builder().clientId(1)
                .createDate(LocalDate.now().minusDays(2))
                .build();
        Account account2 = Account.builder().clientId(1)
                .createDate(LocalDate.now().minusDays(3))
                .build();
        Account account3 = Account.builder().clientId(1)
                .createDate(LocalDate.now().minusDays(1))
                .build();
        Account account4 = Account.builder().clientId(1)
                .createDate(LocalDate.now().minusDays(7))
                .build();
        HashSet<Account> accounts = new HashSet<Account>() {{
            add(account1);
            add(account2);
            add(account3);
            add(account4);
        }};

        when(accountRepository.getAllAccountsByClientId(1)).thenReturn(accounts);

        Set <Account> allAccountsByDateMoreThen = accountService.getAllAccountsByDateMoreThen(1, LocalDate.now().minusDays(2));

        assertEquals(1, allAccountsByDateMoreThen.size());
        assertTrue(allAccountsByDateMoreThen.contains(account3));
    }

}