package com.bank.banking_app.service;

import com.bank.banking_app.entity.Account;
import com.bank.banking_app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepo;

    @Override
    public Account saveAccount(Account account) {
        return accountRepo.save(account);
    }
}