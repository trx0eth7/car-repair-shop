package com.trx0eth7.projects.controller.dao.impl;

import com.trx0eth7.projects.controller.dao.AbstractDao;
import com.trx0eth7.projects.model.OrderStatus;
import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class OrderDao extends AbstractDao<Order> {

    public OrderDao(Session session) {
        super(session);
    }

    public Order findById(Long id) {
        Order order = null;
        Session session = getSession();
        try {
            order = (Order) session.get(Order.class, id);
        } catch (Exception ignored) {
        }
        return order;
    }

    public List<Order> findByName(String description) {
        List<Order> orders = Collections.emptyList();
        Session session = getSession();
        try {
            session.getTransaction().begin();
            orders = session.createSQLQuery("SELECT * FROM orders WHERE orders.description = '" + description + "' ORDER BY id")
                    .addEntity(Order.class)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return orders;
    }

    public List<Order> findAll() {
        List<Order> orders = Collections.emptyList();
        Session session = getSession();
        try {
            session.getTransaction().begin();
            orders = session.createSQLQuery("SELECT * FROM orders ORDER BY id")
                    .addEntity(Order.class)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return orders;
    }

    public List<Order> findByStatus(OrderStatus status) {
        List<Order> orders = Collections.emptyList();
        Session session = getSession();
        try {
            session.getTransaction().begin();
            orders  = session.createCriteria(Order.class)
                    .add(Restrictions.eq("orderStatus", status))
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return orders;
    }

    public List<Order> findByCustomer(Customer customer) {
        List<Order> orders = Collections.emptyList();
        Session session = getSession();
        try {
            session.getTransaction().begin();
            orders  = session.createCriteria(Order.class)
                    .add(Restrictions.eq("customer", customer))
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return orders;
    }

    public List<Order> findByMechanic(Mechanic mechanic) {
        List<Order> orders = Collections.emptyList();
        Session session = getSession();
        try {
            session.getTransaction().begin();
            orders  = session.createCriteria(Order.class)
                    .add(Restrictions.eq("mechanic", mechanic))
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return orders;
    }
}
