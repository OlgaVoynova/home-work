package com.sbrf.reboot;

import com.sbrf.reboot.account.entity.Account;
import com.sbrf.reboot.client.entity.Client;
import com.sbrf.reboot.client.repository.ClientRepository;
import com.sbrf.reboot.utils.MainReport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ClientRepository repository = new ClientRepository();
        List<Account> accounts = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            accounts.add(Account.createAccountWithInfo("ACC" + i, i, BigDecimal.valueOf((long) i), LocalDate.of(2021,6,25).plusDays(i)));
            repository.addCustomer(new Client(i,19,"NAME" + i, new HashSet<Account>(accounts)));
        }

        BigDecimal sumTest = sumBalanceNonParallel(repository,LocalDate.of(2021,7,1),LocalDate.of(2021,8,1),18,30);
        BigDecimal sumParallel = MainReport.getTotalsWithCompletableFuture(repository.getCustomersStream(),LocalDate.of(2021,7,1),LocalDate.of(2021,8,1),18,30);
        BigDecimal sumReactor = MainReport.getTotalsWithReact(repository.getCustomersStream(),LocalDate.of(2021,7,1),LocalDate.of(2021,8,1),18,30);
        System.out.println(sumParallel);
        System.out.println(sumTest);
        System.out.println(sumReactor);

    }

    private static BigDecimal sumBalanceNonParallel (ClientRepository repository, LocalDate dateFrom, LocalDate dateTo, int ageFrom, int ageTo) {
        System.out.println("Время старта последовательного подсчета: "  + LocalTime.now());
        BigDecimal sum = BigDecimal.ZERO;
        Set<Account> goodAccounts = new HashSet<>();
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

