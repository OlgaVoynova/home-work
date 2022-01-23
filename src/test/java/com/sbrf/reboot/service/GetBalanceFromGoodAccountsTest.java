package com.sbrf.reboot.service;

import com.sbrf.reboot.account.entity.Account;
import com.sbrf.reboot.client.entity.Client;
import com.sbrf.reboot.client.repository.ClientRepository;
import com.sbrf.reboot.utils.MainReport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class GetBalanceFromGoodAccountsTest {

    ClientRepository repository;

    private void createData() {
        repository = new ClientRepository();
        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            accounts.add(Account.createAccountWithInfo("ACC" + i, i, BigDecimal.valueOf((long) i), LocalDate.of(2021,6,25).plusDays(i)));
            repository.addCustomer(new Client(i,19,"NAME" + i, new HashSet<Account>(accounts)));
        }
    }

    @Test
    public void sumBalanceByCompletableFutureTest() {
        createData();
        BigDecimal sumTest = sumBalanceNonParallel(LocalDate.of(2021,7,1),LocalDate.of(2021,8,1),18,30);
        Assertions.assertEquals(sumTest, MainReport.getTotalsWithCompletableFuture(repository.getCustomersStream(),LocalDate.of(2021,7,1),LocalDate.of(2021,8,1),18,30));
    }

    private BigDecimal sumBalanceNonParallel (LocalDate dateFrom, LocalDate dateTo, int ageFrom, int ageTo) {
        System.out.println("Время старта последовательного подсчета: "  + LocalTime.now());
        BigDecimal sum = BigDecimal.ZERO;
        Set<Account> goodAccounts = new HashSet<>();
        //Set<Account> accounts = repository.getCustomersStream().filter(client-> client.getAge() < ageFrom && client.getAge() < ageTo && client.getAccounts().stream().filter(acc -> acc.getCreateDate().isAfter(dateFrom) && acc.getCreateDate().isBefore(dateTo)).collect(Collectors.toList()).);
        for (Client client : repository.getCustomersStream().collect(Collectors.toList())) {
            if (client.getAge() > ageFrom && client.getAge() < ageTo) {
                goodAccounts.addAll(client.getAccounts().stream().filter(acc -> acc.getCreateDate().isAfter(dateFrom) && acc.getCreateDate().isBefore(dateTo)).collect(Collectors.toSet()));
            }
        }
        for (Account account : goodAccounts) {
            sum = sum.add(account.getBalance());
        }
        System.out.println("Время окончания последовательного подсчета: "  + LocalTime.now());
        return sum;
    }


}
