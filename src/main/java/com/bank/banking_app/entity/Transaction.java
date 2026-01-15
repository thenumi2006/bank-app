package com.bank.banking_app.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal amount;
    private String transactionType;

    public Transaction() {}

    public Transaction(String sender, String receiver, BigDecimal amount, String type) {
        this.senderAccountNumber = sender;
        this.receiverAccountNumber = receiver;
        this.amount = amount;
        this.transactionType = type;
    }

    public String getSenderAccountNumber() { return senderAccountNumber; }
    public void setSenderAccountNumber(String s) { this.senderAccountNumber = s; }

    public String getReceiverAccountNumber() { return receiverAccountNumber; }
    public void setReceiverAccountNumber(String r) { this.receiverAccountNumber = r; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal a) { this.amount = a; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String t) { this.transactionType = t; }
}