package com.maksym.customermanager;

import com.maksym.customermanager.repository.hibernate.HibernateAccountRepositoryImpl;
import com.maksym.customermanager.repository.hibernate.HibernateCustomerRepositoryImpl;
import com.maksym.customermanager.repository.hibernate.HibernateTransactionRepositoryImpl;
import com.maksym.customermanager.util.Initializer;

public class Main {
    public static void main(String[] args) {
        Initializer.init();
        HibernateAccountRepositoryImpl accountRepository = new HibernateAccountRepositoryImpl();
        HibernateCustomerRepositoryImpl customerRepository = new HibernateCustomerRepositoryImpl();
        HibernateTransactionRepositoryImpl transactionRepository = new HibernateTransactionRepositoryImpl();
        System.out.println("====================================" );
        System.out.println(accountRepository.getAll());
        System.out.println("====================================" );
        System.out.println(customerRepository.getAll());
        System.out.println("====================================" );
        System.out.println(transactionRepository.getAll());

    }
}
