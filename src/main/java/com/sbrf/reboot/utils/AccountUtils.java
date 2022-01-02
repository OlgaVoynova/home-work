package com.sbrf.reboot.utils;

import com.sbrf.reboot.account.entity.Account;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AccountUtils {
    /**
     * Сортировка списка счетов по Id
     * @param accounts списко счетов для сортировки
     * @return список счетов, отсортированный по id
     */
    public static List<Account> sortedById(List<Account> accounts) {
        try {
            Collections.sort(accounts);
        } catch (NullPointerException npe) {
            System.out.println("WARNING: В списке для сортировке обнаружен null-объект, список отсортирован не будет!");
            throw npe;
        }
        return accounts;
    }
    /**
     * Сортировка списка счетов по Id и дате создания
     * @param accounts список счетов для сортировки
     * @return список счетов, отсортированный по id и дате создания
     */
    public static List<Account> sortedByIdDate (List<Account> accounts) {
        Comparator<Account> comparator = getComparatorByIdDate();
        try {
        Collections.sort(accounts,comparator);
        } catch (NullPointerException npe) {
            System.out.println("WARNING: В списке для сортировке обнаружен null-объект, список отсортирован не будет!");
            throw npe;
        }
        return accounts;
    }

    /**
     * Сортировка списка счетов по Id, дате создания, остатку на счете
     * @param accounts списко счетов для сортировки
     * @return отсортированный список счетов
     */
    public static List<Account> sortedByIdDateBalance (List<Account> accounts) {
        Comparator <Account> comparator = getComparatorByIdDateBalance();
        try {
            Collections.sort(accounts,comparator);
        } catch (NullPointerException npe) {
            System.out.println("WARNING: В списке для сортировке обнаружен null-объект, список отсортирован не будет!");
            throw npe;
        }
        return accounts;
    }

    private static Comparator<Account> getComparatorByIdDate () {
        return Comparator.comparing(Account::getId).thenComparing(Account::getCreateDate);
    }

    private static Comparator<Account> getComparatorByIdDateBalance () {
        return Comparator.comparing(Account::getId).thenComparing(Account::getCreateDate).thenComparing(Account::getBalance);
    }

    public static Comparator<Account> getComparatorByBalance () {
        return Comparator.comparing(Account::getBalance);
    }
}
