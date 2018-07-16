package com.trx0eth7.projects.controller;

import com.trx0eth7.projects.controller.dao.hibernate.HSQLDataBaseDao;
import com.trx0eth7.projects.controller.dao.impl.CustomerDao;
import com.trx0eth7.projects.controller.dao.impl.MechanicDao;
import com.trx0eth7.projects.controller.dao.impl.OrderDao;
import com.trx0eth7.projects.model.OrderStatus;
import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import org.hibernate.Session;

import java.util.List;

public class EntityController implements Controller {

    private CustomerDao customerDao;
    private MechanicDao mechanicDao;
    private OrderDao orderDao;
    private Session session;

    public Session getSession() {
        if (session == null) {
            session = HSQLDataBaseDao.getInstance().buildSessionFactoryByDefaultConfiguration().openSession();
        }
        return session;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setMechanicDao(MechanicDao mechanicDao) {
        this.mechanicDao = mechanicDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void addCustomer(Customer customer) {
        customerDao.insert(customer);
    }

    public void updateCustomer(Customer customer) {
        customerDao.update(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerDao.findById(id);
    }

    public List<Customer> getCustomerByName(String name) {
        return customerDao.findByName(name);
    }

    public void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }

    public void addMechanic(Mechanic mechanic) {
        mechanicDao.insert(mechanic);
    }

    public void updateMechanic(Mechanic mechanic) {
        mechanicDao.update(mechanic);
    }

    public List<Mechanic> getAllMechanics() {
        return mechanicDao.findAll();
    }

    public Mechanic getMechanicById(Long id) {
        return mechanicDao.findById(id);
    }

    public List<Mechanic> getMechanicByName(String name) {
        return mechanicDao.findByName(name);
    }

    public void deleteMechanic(Mechanic mechanic) {
        mechanicDao.delete(mechanic);
    }

    public void addOrder(Order order) {
        orderDao.insert(order);
    }

    public void updateOrder(Order order) {
        orderDao.update(order);
    }

    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    public Order getOrderById(Long id) {
        return orderDao.findById(id);
    }

    public void deleteOrder(Order order) {
        orderDao.delete(order);
    }

    public List<Order> getOrderByDescription(String description) {
        return orderDao.findByName(description);
    }

    public List<Order> getOrderByStatus(OrderStatus status) {
        return orderDao.findByStatus(status);
    }

    public List<Order> getOrderByCustomer(Customer customer) {
        return orderDao.findByCustomer(customer);
    }

    public List<Order> getOrderByMechanic(Mechanic mechanic) {
        return orderDao.findByMechanic(mechanic);
    }
}
