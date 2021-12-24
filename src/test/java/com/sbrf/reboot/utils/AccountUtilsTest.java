package com.sbrf.reboot.utils;

import com.sbrf.reboot.account.entity.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountUtilsTest {

    @Test
    void sortedById() {
        List<Account> accounts = new ArrayList<Account>() {{
            add(Account.builder().id("3").createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(Account.builder().id("1").createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(Account.builder().id("3").createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(Account.builder().id("2").createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
        }};

        AccountUtils.sortedById(accounts);

        Assertions.assertEquals("1", accounts.get(0).getId());
        Assertions.assertEquals("2", accounts.get(1).getId());
        Assertions.assertEquals("3", accounts.get(2).getId());
        Assertions.assertEquals("3", accounts.get(3).getId());

    }

    @Test
    void sortedByIdWithNull() {
        List<Account> accounts = new ArrayList<Account>() {{
            add(Account.builder().id("3").createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
            add(null);
        }};

        assertThrows(NullPointerException.class,() -> AccountUtils.sortedById(accounts));
    }

    @Test
    void sortedByIdDate() {
        List<Account> accounts = new ArrayList<Account>() {{
            add(Account.builder().id("1").createDate(LocalDate.now().minusDays(4)).balance(BigDecimal.TEN).build());
            add(Account.builder().id("3").createDate(LocalDate.now().minusDays(3)).balance(BigDecimal.TEN).build());
            add(Account.builder().id("3").createDate(LocalDate.now().minusDays(1)).balance(BigDecimal.TEN).build());
            add(Account.builder().id("2").createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
        }};

        AccountUtils.sortedByIdDate(accounts);

        Assertions.assertEquals("1", accounts.get(0).getId());
        Assertions.assertEquals("2", accounts.get(1).getId());
        Assertions.assertEquals(LocalDate.now().minusDays(3), accounts.get(2).getCreateDate());
        Assertions.assertEquals(LocalDate.now().minusDays(1), accounts.get(3).getCreateDate());
    }

    @Test
    void sortedByIdDateBalance() {
        List<Account> accounts = new ArrayList<Account>() {{
            add(Account.builder().id("1").createDate(LocalDate.now().minusDays(4)).balance(BigDecimal.TEN).build());
            add(Account.builder().id("3").createDate(LocalDate.now().minusDays(3)).balance(BigDecimal.TEN).build());
            add(Account.builder().id("3").createDate(LocalDate.now().minusDays(3)).balance(BigDecimal.ONE).build());
            add(Account.builder().id("1").createDate(LocalDate.now()).balance(BigDecimal.TEN).build());
        }};

        AccountUtils.sortedByIdDateBalance(accounts);

        Assertions.assertEquals(LocalDate.now().minusDays(4),accounts.get(0).getCreateDate());
        Assertions.assertEquals(LocalDate.now(),accounts.get(1).getCreateDate());
        Assertions.assertEquals(BigDecimal.ONE, accounts.get(2).getBalance());
        Assertions.assertEquals(BigDecimal.TEN, accounts.get(3).getBalance());
    }
}