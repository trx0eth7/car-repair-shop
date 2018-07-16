package com.trx0eth7.projects.controller.dao.impl;

import com.trx0eth7.projects.controller.dao.AbstractDao;
import com.trx0eth7.projects.model.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class CustomerDao extends AbstractDao<Customer> {

    public CustomerDao(Session session) {
        super(session);
    }

    @Nullable
    public Customer findById(Long id) {
        Customer customer = null;
        Session session = getSession();
        try {
            customer = (Customer) session.get(Customer.class, id);
        } catch (Exception ignored) {
        }
        return customer;
    }

    public List<Customer> findByName(String name) {
        List<Customer> customers = Collections.emptyList();
        Session session = getSession();
        try {
            session.getTransaction().begin();
            customers = session.createSQLQuery("SELECT * FROM customers WHERE customers.firstName = '" + name + "' ORDER BY firstName")
                    .addEntity(Customer.class)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return customers;
    }

    public List<Customer> findAll() {
        List<Customer> customers = Collections.emptyList();
        Session session = getSession();
        try {
            session.getTransaction().begin();
            customers = session.createSQLQuery("SELECT * FROM customers ORDER BY id")
                    .addEntity(Customer.class)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return customers;
    }
}
