package com.maksym.customermanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Column(name = "amount")
    private BigDecimal amount;

    public Transaction() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + super.getId() + '\'' +
                "amount=" + amount +
                '}';
    }
}
