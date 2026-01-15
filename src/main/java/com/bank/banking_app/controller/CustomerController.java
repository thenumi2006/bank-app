package com.bank.banking_app.controller;

import com.bank.banking_app.entity.Account;
import com.bank.banking_app.entity.User;
import com.bank.banking_app.entity.Transaction; // Make sure this is YOUR entity
import com.bank.banking_app.repository.AccountRepository;
import com.bank.banking_app.repository.TransactionRepository;
import com.bank.banking_app.repository.UserRepository; // Added missing import
import com.bank.banking_app.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired private BankService bankService;
    @Autowired private AccountRepository accountRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private TransactionRepository transactionRepo;

    @GetMapping("/home")
    public String customerHome(Principal principal, Model model) {
        model.addAttribute("name", principal.getName());
        return "customer-home";
    }

    @GetMapping("/transfer")
    public String showTransferPage() {
        return "customer-transfer";
    }

    @PostMapping("/transfer")
    public String processTransfer(@RequestParam String fromAcc,
                                  @RequestParam String toAcc,
                                  @RequestParam BigDecimal amount,
                                  Model model) {
        try {
            bankService.transferFunds(fromAcc, toAcc, amount);
            return "redirect:/customer/home?success";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());

            return "customer-transfer";
        }
    }


    @GetMapping("/history")
    public String viewHistory(Principal principal, Model model) {
        User user = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Account> accounts = accountRepo.findByUser(user);

        if (!accounts.isEmpty()) {
            String accNum = accounts.get(0).getAccountNumber();

            List<Transaction> transactions = transactionRepo
                    .findBySenderAccountNumberOrReceiverAccountNumberOrderByCreatedAtDesc(accNum, accNum);

            model.addAttribute("transactions", transactions);
            model.addAttribute("myAccountNumber", accNum);
        }

        return "customer-history";
    }
    @GetMapping("/details")
    public String viewCustomerDetails(Principal principal, Model model) {

        User user = userRepo.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = accountRepo.findByUser(user).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No account found for this user"));

        model.addAttribute("user", user);
        model.addAttribute("account", account);

        return "customer-details";
    }
}