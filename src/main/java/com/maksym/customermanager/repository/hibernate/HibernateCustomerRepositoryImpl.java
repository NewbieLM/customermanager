package com.maksym.customermanager.repository.hibernate;

import com.maksym.customermanager.model.Customer;
import com.maksym.customermanager.repository.CustomerRepository;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;

import static com.maksym.customermanager.util.Initializer.getSession;

public class HibernateCustomerRepositoryImpl implements CustomerRepository {

    public List<Customer> getAll() {
        Session session = getSession();
        List<Customer> customers = session.createQuery("FROM Customer").getResultList();
        session.close();
        return customers;
    }


    public Customer get(Long customerId) {
        Session session = getSession();
        Query query = session.createQuery("FROM Customer c JOIN FETCH c.account WHERE c.id=:id");
        query.setParameter("id", customerId);
        Customer customer = (Customer) query.getSingleResult();
        session.close();
        return customer;
    }


    public Customer saveOrUpdate(Customer customer) {
        Session session = getSession();
        session.beginTransaction();
        session.saveOrUpdate(customer);
        session.getTransaction().commit();
        session.close();
        return customer;
    }


    public boolean remove(Long customerId) {
        Session session = getSession();
        session.beginTransaction();
        Query query = session.createQuery("DELETE FROM Customer c WHERE c.id=: id");
        boolean deleted = query.setParameter("id", customerId).executeUpdate() != 0;
        session.getTransaction().commit();
        session.close();
        return deleted;
    }
}
