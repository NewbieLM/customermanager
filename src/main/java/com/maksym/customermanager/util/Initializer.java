package com.maksym.customermanager.util;

import com.maksym.customermanager.repository.AccountRepository;
import com.maksym.customermanager.repository.CustomerRepository;
import com.maksym.customermanager.repository.TransactionRepository;
import com.maksym.customermanager.repository.hibernate.HibernateAccountRepositoryImpl;
import com.maksym.customermanager.repository.hibernate.HibernateCustomerRepositoryImpl;
import com.maksym.customermanager.repository.hibernate.HibernateTransactionRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Initializer implements ServletContextListener {
    private static volatile SessionFactory sessionFactory;
    private static CustomerRepository customerRepository;
    private static AccountRepository accountRepository;
    private static TransactionRepository transactionRepository;

    public void contextInitialized(ServletContextEvent sce) {
        init();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        if (sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }

    private static void init() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        customerRepository = new HibernateCustomerRepositoryImpl();
        accountRepository = new HibernateAccountRepositoryImpl();
        transactionRepository = new HibernateTransactionRepositoryImpl();
    }

    public static Session getSession() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
        } catch (Throwable t) {
            System.err.println("Exception while getting session.. ");
            t.printStackTrace();
        }
        return session;
    }

    public static CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public static AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public static TransactionRepository getTransactionRepository() {
        return transactionRepository;
    }
}
