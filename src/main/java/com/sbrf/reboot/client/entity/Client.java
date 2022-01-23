package com.sbrf.reboot.client.entity;

import com.sbrf.reboot.account.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class Client {
    private int id;
    private int age;
    private String name;
    private Set<Account> accounts = new HashSet<>();

    public void addAccount (Account account) {
        this.accounts.add(account);
    }
}
