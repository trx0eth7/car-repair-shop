package com.trx0eth7.projects.controller;

import com.trx0eth7.projects.controller.dao.impl.CustomerDao;
import com.trx0eth7.projects.controller.dao.impl.MechanicDao;
import com.trx0eth7.projects.controller.dao.impl.OrderDao;
import com.trx0eth7.projects.model.OrderStatus;
import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;

import java.util.List;

public class EntityController implements Controller {

    private CustomerDao customerDao;
    private MechanicDao mechanicDao;
    private OrderDao orderDao;

    void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    void setMechanicDao(MechanicDao mechanicDao) {
        this.mechanicDao = mechanicDao;
    }

    void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    void addCustomer(Customer customer) {
        customerDao.insert(customer);
    }

    void updateCustomer(Customer customer) {
        customerDao.update(customer);
    }

    List<Customer> getAllCustomers() {
        return customerDao.findAll();
    }

    Customer getCustomerById(Long id) {
        return customerDao.findById(id);
    }

    List<Customer> getCustomerByName(String name) {
        return customerDao.findByName(name);
    }

    void deleteCustomer(Customer customer) {
        customerDao.delete(customer);
    }

    void addMechanic(Mechanic mechanic) {
        mechanicDao.insert(mechanic);
    }

    void updateMechanic(Mechanic mechanic) {
        mechanicDao.update(mechanic);
    }

    List<Mechanic> getAllMechanics() {
        return mechanicDao.findAll();
    }

    Mechanic getMechanicById(Long id) {
        return mechanicDao.findById(id);
    }

    List<Mechanic> getMechanicByName(String name) {
        return mechanicDao.findByName(name);
    }

    void deleteMechanic(Mechanic mechanic) {
        mechanicDao.delete(mechanic);
    }

    void addOrder(Order order) {
        orderDao.insert(order);
    }

    void updateOrder(Order order) {
        orderDao.update(order);
    }

    List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    Order getOrderById(Long id) {
        return orderDao.findById(id);
    }

    void deleteOrder(Order order) {
        orderDao.delete(order);
    }

    List<Order> getOrderByDescription(String description) {
        return orderDao.findByName(description);
    }

    List<Order> getOrderByStatus(OrderStatus status) {
        return orderDao.findByStatus(status);
    }

    List<Order> getOrderByCustomer(Customer customer) {
        return orderDao.findByCustomer(customer);
    }

    List<Order> getOrderByMechanic(Mechanic mechanic) {
        return orderDao.findByMechanic(mechanic);
    }
}
