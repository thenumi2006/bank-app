package com.bank.banking_app.controller;

import com.bank.banking_app.entity.Account;
import com.bank.banking_app.entity.User;
import com.bank.banking_app.repository.AccountRepository;
import com.bank.banking_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private AccountRepository accountRepo;
    @Autowired private UserRepository userRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping("/home")
    public String adminHome(Model model) {
        model.addAttribute("recentAccounts", accountRepo.findAll());
        return "admin-home";
    }

    @GetMapping("/create-account")
    public String showCreateAccount() {
        return "admin-create-account";
    }


    @PostMapping("/create-account")
    public String processCreateAccount(@RequestParam String username,
                                       @RequestParam String password,
                                       @RequestParam String fullName,
                                       @RequestParam String accountNumber,
                                       @RequestParam String accountType,
                                       @RequestParam BigDecimal balance) {

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setFullName(fullName);
        newUser.setRole("ROLE_CUSTOMER");
        newUser.setPassword(passwordEncoder.encode(password));
        userRepo.save(newUser);

        Account newAccount = new Account();
        newAccount.setAccountNumber(accountNumber);
        newAccount.setAccountType(accountType);
        newAccount.setBalance(balance);
        newAccount.setUser(newUser);
        accountRepo.save(newAccount);

        return "redirect:/admin/home?success";
    }
    @GetMapping("/all-accounts")
    public String listAllAccounts(Model model) {
        model.addAttribute("accounts", accountRepo.findAll());
        return "admin-all-accounts";
    }

    @GetMapping("/delete-account/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountRepo.deleteById(id);
        return "redirect:/admin/all-accounts?deleted";
    }

    @GetMapping("/edit-account/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        model.addAttribute("account", account);
        return "admin-edit-account";
    }

    @PostMapping("/update-account")
    public String updateAccount(@ModelAttribute Account account) {
        Account existing = accountRepo.findById(account.getId()).orElseThrow();
        existing.setAccountType(account.getAccountType());
        existing.setBalance(account.getBalance());

        accountRepo.save(existing);
        return "redirect:/admin/all-accounts?updated";
    }
}