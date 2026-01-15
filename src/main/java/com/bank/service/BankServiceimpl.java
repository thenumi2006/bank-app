package com.bank.banking_app.service;


import com.bank.banking_app.entity.Account;
import java.math.BigDecimal;

public interface BankService {

    Account createAccount(Account account, Long userId);

}