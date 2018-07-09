package com.trx0eth7.projects.controller.dao.impl;

import com.trx0eth7.projects.controller.dao.AbstractDao;
import com.trx0eth7.projects.model.entity.Customer;
import org.hibernate.Session;

public class CustomerDao extends AbstractDao<Customer> {

    public CustomerDao(Session session) {
        super(session);
    }

    public Customer get(long id) {
        return null;
    }
}
