package com.trx0eth7.projects.controller;

import com.trx0eth7.projects.controller.repositories.CustomerRepository;
import com.trx0eth7.projects.controller.repositories.MechanicRepository;
import com.trx0eth7.projects.controller.repositories.OrderRepository;
import com.trx0eth7.projects.model.OrderStatus;
import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Controller
public class EntityController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MechanicRepository mechanicRepository;
    @Autowired
    private OrderRepository orderRepository;

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (Customer customer : customerRepository.findAll()) {
            customers.add(customer);
        }
        return customers;
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findOne(id);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    public void deleteCustomers(List<Customer> customers) {
        for (Customer customer : customers) {
            deleteCustomer(customer);
        }
    }

    public void addMechanic(Mechanic mechanic) {
        mechanicRepository.save(mechanic);
    }

    public void updateMechanic(Mechanic mechanic) {
        mechanicRepository.save(mechanic);
    }

    public List<Mechanic> getAllMechanics() {
        List<Mechanic> mechanics = new ArrayList<>();
        for (Mechanic mechanic : mechanicRepository.findAll()) {
            mechanics.add(mechanic);
        }
        return mechanics;
    }

    public Mechanic getMechanicById(Long id) {
        return mechanicRepository.findOne(id);
    }

    public void deleteMechanic(Mechanic mechanic) {
        mechanicRepository.delete(mechanic);
    }

    public void deleteMechanics(List<Mechanic> mechanics) {
        for (Mechanic mechanic : mechanics) {
            deleteMechanic(mechanic);
        }
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            orders.add(order);
        }
        return orders;
    }

    public Order getOrderById(Long id) {
        return orderRepository.findOne(id);
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    public void deleteOrders(List<Order> orders) {
        for (Order order : orders) {
            orderRepository.delete(order);
        }
    }

    public List<Order> getOrderByDescription(String description) {
        return orderRepository.findByDescriptionLike(description);
    }

    public List<Order> getOrderByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatus(status);
    }

    public List<Order> getOrdersByCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }

    public List<Order> getOrdersByMechanic(Mechanic mechanic) {
        return orderRepository.findByMechanic(mechanic);
    }
}
