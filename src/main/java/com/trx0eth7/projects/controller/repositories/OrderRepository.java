package com.trx0eth7.projects.controller.repositories;

import com.trx0eth7.projects.model.OrderStatus;
import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByDescriptionLike(String description);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findByCustomer(Customer customer);

    List<Order> findByMechanic(Mechanic mechanic);
}
