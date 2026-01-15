package com.bank.banking_app.controller;

import com.bank.banking_app.repository.AccountRepository;
import com.bank.banking_app.repository.TransactionRepository;
import com.bank.banking_app.repository.UserRepository;
import com.bank.banking_app.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

}