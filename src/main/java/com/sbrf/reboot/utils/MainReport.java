package com.sbrf.reboot.utils;

import com.sbrf.reboot.account.entity.Account;
import com.sbrf.reboot.client.entity.Client;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainReport {
    public static BigDecimal getTotalsWithCompletableFuture (Stream<Client> customers, LocalDate dateFrom, LocalDate dateTo, int ageFrom, int ageTo) {
        System.out.println("Время старта параллельного подсчета: "  + LocalTime.now());
        BigDecimal sum = BigDecimal.ZERO;
        /* Завести CF с кодом подсчета */
        /* Если моя база клиентов разрастется, я буду бить не по одному пользователю, а по пачкам, и генерить Completable Future-ы для пачек customer-ов */
        List<CompletableFuture<Set<Account>>> futures = customers.map(client -> getBalanceSubFlow(client,dateFrom,dateTo,ageFrom,ageTo)).collect(Collectors.toList());

        /* запустить CF, собрать результаты */
        CompletableFuture<Void> futureStarter = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        CompletableFuture<List<Set<Account>>> listofSetOfGoodAccounts = futureStarter.thenApply(x -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        Set<Account> distinctGoodAccounts = new HashSet<>();
        try {
            for (Set<Account> accountSet : listofSetOfGoodAccounts.get()) {
                distinctGoodAccounts.addAll(accountSet);
            }
            for (Account account : distinctGoodAccounts) {
                sum = sum.add(account.getBalance());
            }
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("В ходе подсчета суммы балансов возникли проблемы!");
        }
        System.out.println("Время окончания параллельного подсчета: "  + LocalTime.now());
        return sum;
    };

    public static BigDecimal getTotalsWithReact (Stream<Client> customers, LocalDate dateFrom, LocalDate dateTo, int ageFrom, int ageTo) {
        System.out.println("Время старта параллельного подсчета REACTOR: "  + LocalTime.now());
        BigDecimal sum = BigDecimal.ZERO;
        Set<Account> distinctGoodAccounts = new HashSet<>();
        Scheduler scheduler = Schedulers.newParallel("parallelFluxScheduler");
        Flux.fromStream(customers)
                .parallel()
                .runOn(scheduler)
                .map(customer -> getAccountIfClientOk(customer,dateFrom,dateTo,ageFrom,ageTo))
                .subscribe(distinctGoodAccounts::addAll);
        for (Account account : distinctGoodAccounts) {
            sum = sum.add(account.getBalance());
        }
        /* Надо принудительно завершить, иначе наше приложение остается работать даже после окончания работы потока Main*/
        scheduler.dispose();
        System.out.println("Время окончания параллельного подсчета REACTOR: "  + LocalTime.now());
        return sum;
    }

    private static CompletableFuture<Set<Account>> getBalanceSubFlow (Client client, LocalDate dateFrom, LocalDate dateTo, int ageFrom, int ageTo) {
        return CompletableFuture.supplyAsync(() -> {
            Set<Account> goodAccounts = new HashSet<>();
            if (client.getAge() > ageFrom && client.getAge() < ageTo) {
                goodAccounts = client.getAccounts().stream().filter(acc -> acc.getCreateDate().isAfter(dateFrom) && acc.getCreateDate().isBefore(dateTo)).collect(Collectors.toSet());
            }
            return goodAccounts;
        });
    }

    private static Set<Account> getAccountIfClientOk (Client client, LocalDate dateFrom, LocalDate dateTo, int ageFrom, int ageTo) {
        Set<Account> accounts = new HashSet<>();
        if (client.getAge() > ageFrom && client.getAge() < ageTo) {
            for (Account account : client.getAccounts()) {
               if (account.getCreateDate().isAfter(dateFrom)
                       && account.getCreateDate().isBefore(dateTo)) {
                   accounts.add(account);
               }
            }
        }
        return accounts;
    }

}
