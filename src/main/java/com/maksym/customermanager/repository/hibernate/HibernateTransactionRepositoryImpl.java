package com.maksym.customermanager.repository.hibernate;

import com.maksym.customermanager.model.Transaction;
import com.maksym.customermanager.repository.TransactionRepository;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

import static com.maksym.customermanager.util.Initializer.getSession;

public class HibernateTransactionRepositoryImpl implements TransactionRepository {

    public List<Transaction> getAll() {
        Session session = getSession();
        List<Transaction> transaction = session.createQuery("FROM Transaction ").getResultList();
        session.close();
        return transaction;
    }


    public Transaction get(Long trasactionId) {
        Session session = getSession();
        Query query = session.createQuery("FROM Transaction t JOIN FETCH t.account WHERE t.id=: id");
        query.setParameter("id", trasactionId);
        Transaction transaction = (Transaction) query.getSingleResult();
        session.close();
        return transaction;
    }


    public Transaction saveOrUpdate(Transaction transaction) {
        Session session = getSession();
        session.beginTransaction();
        session.saveOrUpdate(transaction);
        session.getTransaction().commit();
        session.close();
        return transaction;
    }


    public boolean remove(Long trasactionId) {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Transaction t WHERE t.id=: id");
        boolean deleted = query.setParameter("id", trasactionId).executeUpdate() != 0;
        session.getTransaction().commit();
        session.close();
        return deleted;
    }
}
