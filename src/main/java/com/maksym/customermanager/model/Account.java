package com.maksym.customermanager.model;

import org.hibernate.Hibernate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Column(name = "account_data", nullable = false)
    private String accountData;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<Transaction>();

    @Column(name = "balance")
    private BigDecimal balance;

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
        String str = "Account{" +
                "id='" + super.getId() + '\'' +
                "accountData='" + accountData + '\'' +
                ", balance=" + balance +
                "}\n";
        if (Hibernate.isInitialized(transactions) && transactions != null && transactions.size() > 0) {
            str += transactionsToString();
        }
        return str;
    }

    private String transactionsToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Transactions:");
        stringBuilder.append("\n");
        for (Transaction transaction : transactions) {
            stringBuilder.append("-");
            stringBuilder.append(transaction);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
}


