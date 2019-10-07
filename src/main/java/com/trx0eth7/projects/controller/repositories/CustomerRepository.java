package com.trx0eth7.projects.controller.repositories;

import com.trx0eth7.projects.model.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
