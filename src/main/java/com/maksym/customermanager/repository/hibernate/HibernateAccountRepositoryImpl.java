package com.maksym.customermanager.repository.hibernate;

import com.maksym.customermanager.model.Account;
import com.maksym.customermanager.repository.AccountRepository;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

import static com.maksym.customermanager.util.Initializer.getSession;

public class HibernateAccountRepositoryImpl implements AccountRepository {

    public List<Account> getAll() {
        Session session = getSession();
        List<Account> accounts = session.createQuery("FROM Account").getResultList();
        session.close();
        return accounts;
    }


    public Account get(Long accountId) {
        Session session = getSession();
        Query query = session.createQuery("FROM Account a JOIN FETCH a.transactions WHERE a.id=: id");
        query.setParameter("id", accountId);
        Account account = (Account) query.getSingleResult();
        session.close();
        return account;
    }


    public Account saveOrUpdate(Account account) {
        Session session = getSession();
        session.beginTransaction();
        session.saveOrUpdate(account);
        session.getTransaction().commit();
        session.close();
        return account;
    }


    public boolean remove(Long accountId) {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Account a WHERE a.id=: id");
        boolean deleted = query.setParameter("id", accountId).executeUpdate() != 0;
        session.getTransaction().commit();
        session.close();
        return deleted;
    }
}
