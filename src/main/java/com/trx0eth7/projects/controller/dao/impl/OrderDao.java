package com.trx0eth7.projects.controller.dao.impl;

import com.trx0eth7.projects.controller.dao.AbstractDao;
import com.trx0eth7.projects.model.entity.Order;
import org.hibernate.Session;

public class OrderDao extends AbstractDao<Order> {

    protected OrderDao(Session session) {
        super(session);
    }

    public Order get(long id) {
        return null;
    }
}
