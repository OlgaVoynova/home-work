package com.sbrf.reboot.account.service;


import com.sbrf.reboot.account.entity.Account;

import java.time.LocalDate;
import java.util.Set;

public interface AccountService {

    Account createAccount(Integer clientId);
    void deleteAccount(Account account);
    boolean isAccountExist(Integer id, Account account) ;
    int countEmptyAccounts();
    Account getMaxAccountBalance(Integer clientId);
    Set<Account> getAllAccountsByDateMoreThen (Integer clientId, LocalDate date);
}
