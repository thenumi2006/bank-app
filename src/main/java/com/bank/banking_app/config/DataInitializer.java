package com.bank.banking_app.config;

import com.bank.banking_app.entity.User;
import com.bank.banking_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private UserRepository userRepo;
    @Autowired private PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if(userRepo.count() == 0) {
            User admin = new User("admin", encoder.encode("123"), "System Admin", "ADMIN");
            userRepo.save(admin);
        }
    }
}