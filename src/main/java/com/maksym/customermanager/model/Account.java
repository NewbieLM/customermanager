package com.maksym.customermanager.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Column(name = "account_data", nullable = false)
    private String accountData;

    @OneToMany
    @JoinColumn(name = "account_id")
    private List<Transaction> transactions;

    @Column(name = "balance")
    private BigDecimal balance;

/*    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @PrimaryKeyJoinColumn
    private Customer customer;*/

    public Account() {
    }

    public Account(String accountData, BigDecimal balance) {
        this.accountData = accountData;
        this.balance = balance;

    }

    public String getAccountData() {
        return accountData;
    }

    public void setAccountData(String accountData) {
        this.accountData = accountData;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + super.getId() + '\'' +
                "accountData='" + accountData + '\'' +
                ", balance=" + balance +
                '}';
    }
}


