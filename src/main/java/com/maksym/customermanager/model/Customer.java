package com.maksym.customermanager.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firsName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "specialty", nullable = false)
    private String specialty;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public Customer() {
    }

    public Customer(String firsName, String lastName, String specialty) {
        this.firsName = firsName;
        this.lastName = lastName;
        this.specialty = specialty;
    }


    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + super.getId() + '\'' +
                "firsName='" + firsName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialty='" + specialty + '\'' +
                //   account.toString()+
                '}';
    }
}
