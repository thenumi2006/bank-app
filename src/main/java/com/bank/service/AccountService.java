package com.bank.banking_app.service;


import com.bank.banking_app.entity.Account;
import java.util.List;

public interface AccountService {
    Account saveAccount(Account account);

    List<Account> getAllAccounts();
    Account getAccountByNumber(String accountNumber);

    void deleteAccount(Long id);

}
