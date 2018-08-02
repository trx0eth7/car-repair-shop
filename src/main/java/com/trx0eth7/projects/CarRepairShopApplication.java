package com.trx0eth7.projects;

import com.trx0eth7.projects.controller.repositories.CustomerRepository;
import com.trx0eth7.projects.controller.repositories.MechanicRepository;
import com.trx0eth7.projects.controller.repositories.OrderRepository;
import com.trx0eth7.projects.model.OrderStatus;
import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class CarRepairShopApplication {
    public static void main(String... args) {
        SpringApplication.run(CarRepairShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner ensureTestData(CustomerRepository customerRepository,
                                            MechanicRepository mechanicRepository,
                                            OrderRepository orderRepository) {
        return (args) -> {
            Customer customerA = new Customer("Vasya", "Ivanov", "Ivanovich", "+79270234456");
            Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");
            Customer customerC = new Customer("Vadim", "Vnukov", "Ivanovich", "+79202398637");
            Customer customerD = new Customer("Vasya", "Sidorov", "Michaylovich", "+79372393445");
            Customer customerE = new Customer("Alina", "Pivovar", "Valerevna", "+79179790014");

            Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", 150);
            Mechanic mechanicB = new Mechanic("Boris", "Ivanov", "Gennadevich", 200);
            Mechanic mechanicC = new Mechanic("Gleb", "Sidorov", "Ivanovich", 230);

            DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
            Date startDate = null;
            Date dueDate = null;
            try {
                startDate = dateFormat.parse("2018 07 10");
                dueDate = dateFormat.parse("2018 07 11");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Order orderA = new Order("Wheel replacement", customerA, mechanicA, startDate, dueDate, 500, OrderStatus.PLANNED);
            Order orderB = new Order("Replacing the headlamp", customerB, mechanicB, startDate, dueDate, 700, OrderStatus.PLANNED);
            Order orderC = new Order("Change of oil", customerC, mechanicC, startDate, dueDate, 200, OrderStatus.COMPLETED);
            Order orderD = new Order("Fixing the generator", customerD, mechanicA, startDate, dueDate, 1000, OrderStatus.ACCEPTED);
            Order orderE = new Order("Wheel replacement", customerB, mechanicC, startDate, dueDate, 500, OrderStatus.COMPLETED);
            Order orderF = new Order("Change of oil", customerC, mechanicA, startDate, dueDate, 500, OrderStatus.PLANNED);

            customerRepository.save(customerA);
            customerRepository.save(customerB);
            customerRepository.save(customerC);
            customerRepository.save(customerD);
            customerRepository.save(customerE);

            mechanicRepository.save(mechanicA);
            mechanicRepository.save(mechanicB);
            mechanicRepository.save(mechanicC);

            orderRepository.save(orderA);
            orderRepository.save(orderB);
            orderRepository.save(orderC);
            orderRepository.save(orderD);
            orderRepository.save(orderE);
            orderRepository.save(orderF);
        };
    }
}
