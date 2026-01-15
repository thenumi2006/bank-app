package com.bank.banking_app.service;


import com.bank.banking_app.entity.*;
import com.bank.banking_app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class BankServiceImpl implements BankService {

    @Autowired private AccountRepository accountRepo;
    @Autowired private TransactionRepository transactionRepo;

    @Override
    @Transactional
    public void transferFunds(String fromAcc, String toAcc, BigDecimal amount) {

        Account sender = accountRepo.findByAccountNumber(fromAcc)
                .orElseThrow(() -> new RuntimeException("Error: Sender account " + fromAcc + " does not exist."));

        Account receiver = accountRepo.findByAccountNumber(toAcc)
                .orElseThrow(() -> new RuntimeException("Error: Receiver account " + toAcc + " does not exist."));

        if (fromAcc.equals(toAcc)) {
            throw new RuntimeException("Error: Cannot transfer money to the same account.");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient Funds! Available: RS:" + sender.getBalance());
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepo.save(sender);
        accountRepo.save(receiver);

        Transaction tx = new Transaction();
        tx.setSenderAccountNumber(fromAcc);
        tx.setReceiverAccountNumber(toAcc);
        tx.setAmount(amount);
        tx.setTransactionType("TRANSFER");

        transactionRepo.saveAndFlush(tx);

    }

    @Override
    public Account createAccount(Account account, Long userId) {

        return accountRepo.save(account);
    }
}