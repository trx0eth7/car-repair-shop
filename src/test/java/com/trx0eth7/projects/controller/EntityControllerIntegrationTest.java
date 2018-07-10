package com.trx0eth7.projects.controller;

import com.trx0eth7.projects.controller.dao.hibernate.DataBaseDao;
import com.trx0eth7.projects.controller.dao.hibernate.HSQLDataBaseDao;
import com.trx0eth7.projects.controller.dao.impl.CustomerDao;
import com.trx0eth7.projects.controller.dao.impl.MechanicDao;
import com.trx0eth7.projects.controller.dao.impl.OrderDao;
import com.trx0eth7.projects.model.OrderStatus;
import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class EntityControllerIntegrationTest {

    DataBaseDao db;
    EntityController controller;

    @Before
    public void setUp() {
        db = new HSQLDataBaseDao();
        Session session = db.openSession();
        controller = new EntityController();

        CustomerDao customerDao = new CustomerDao(session);
        MechanicDao mechanicDao = new MechanicDao(session);
        OrderDao orderDao = new OrderDao(session);

        controller.setCustomerDao(customerDao);
        controller.setMechanicDao(mechanicDao);
        controller.setOrderDao(orderDao);
    }

    @After
    public void tearDown() {
        db.closeSession();
    }

    @Test
    public void shouldAddCustomers() {
        Customer customerA = new Customer("Petr", "Ivanov", "Ivanovich", "+79270234456");
        Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");
        Customer customerC = new Customer("Vadim", "Vnukov", "Ivanovich", "+79202398637");

        List<Customer> expectedCustomers = Arrays.asList(customerA, customerB, customerC);

        controller.addCustomer(customerA);
        controller.addCustomer(customerB);
        controller.addCustomer(customerC);

        List<Customer> actualCustomers = controller.getAllCustomers();
        Assert.assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    public void shouldUpdateCustomer() {
        Customer customerA = new Customer("Alina", "Ivanova", "Valerevna", "+79270234456");
        controller.addCustomer(customerA);
        Assert.assertEquals("Ivanova", controller.getCustomerById(1L).getLastName());
        customerA.setLastName("Petrova");
        customerA.setPhone("+79179797322");
        controller.updateCustomer(customerA);
        Assert.assertEquals("Petrova", controller.getCustomerById(1L).getLastName());
        Assert.assertEquals("+79179797322", controller.getCustomerById(1L).getPhone());
    }


    @Test
    public void shouldDeleteCustomer() {
        Customer customerA = new Customer("Petr", "Ivanov", "Ivanovich", "+79270234456");
        Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");

        controller.addCustomer(customerA);
        controller.addCustomer(customerB);
        Assert.assertEquals(2, controller.getAllCustomers().size());
        controller.deleteCustomer(customerB);
        Assert.assertEquals(1, controller.getAllCustomers().size());
    }

    @Test
    public void shouldGetCustomersByName() {
        Customer customerA = new Customer("Vasya", "Ivanov", "Ivanovich", "+79270234456");
        Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");
        Customer customerC = new Customer("Vadim", "Vnukov", "Ivanovich", "+79202398637");
        Customer customerD = new Customer("Vasya", "Sidorov", "Michaylovich", "+79372393445");

        controller.addCustomer(customerA);
        controller.addCustomer(customerB);
        controller.addCustomer(customerC);
        controller.addCustomer(customerD);

        List<Customer> actualCustomers = controller.getCustomerByName("Vasya");
        Assert.assertEquals(3, actualCustomers.size());
    }

    @Test
    public void shouldAddMechanics() {

        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        Mechanic mechanicB = new Mechanic("Boris", "Ivanov", "Gennadevich", "200R");
        Mechanic mechanicC = new Mechanic("Gleb", "Sidorov", "Ivanovich", "230R");

        List<Mechanic> expectedMechanics = Arrays.asList(mechanicA, mechanicB, mechanicC);

        controller.addMechanic(mechanicA);
        controller.addMechanic(mechanicB);
        controller.addMechanic(mechanicC);

        List<Mechanic> actualMechanics = controller.getAllMechanics();
        Assert.assertEquals(expectedMechanics, actualMechanics);
    }

    @Test
    public void shouldUpdateMechanic() {
        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        controller.addMechanic(mechanicA);
        Assert.assertEquals("150R", controller.getMechanicById(1L).getHourlyPay());
        mechanicA.setHourlyPay("170R");
        controller.updateMechanic(mechanicA);
        Assert.assertEquals("170R", controller.getMechanicById(1L).getHourlyPay());
    }


    @Test
    public void shouldDeleteMechanic() {
        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        Mechanic mechanicB = new Mechanic("Boris", "Ivanov", "Gennadevich", "200R");
        controller.addMechanic(mechanicA);
        controller.addMechanic(mechanicB);
        Assert.assertEquals(2, controller.getAllMechanics().size());
        controller.deleteMechanic(mechanicB);
        Assert.assertEquals(1, controller.getAllMechanics().size());
    }

    @Test
    public void shouldGetMechanicByName() {
        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        Mechanic mechanicB = new Mechanic("Boris", "Ivanov", "Gennadevich", "200R");
        Mechanic mechanicC = new Mechanic("Gleb", "Sidorov", "Ivanovich", "230R");
        Mechanic mechanicD = new Mechanic("Alex", "Semenov", "Alexandrovich", "210R");

        controller.addMechanic(mechanicA);
        controller.addMechanic(mechanicB);
        controller.addMechanic(mechanicC);
        controller.addMechanic(mechanicD);

        List<Mechanic> actualMechanic = controller.getMechanicByName("Alex");
        Assert.assertEquals(2, actualMechanic.size());
    }

    @Test
    public void shoudAddOrders() throws ParseException {
        Customer customerA = new Customer("Vasya", "Ivanov", "Ivanovich", "+79270234456");
        Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");
        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date startDate = dateFormat.parse("2018 07 10");
        Date dueDate = dateFormat.parse("2018 07 11");
        Order orderA = new Order("Wheel replacement", customerA, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);
        startDate = dateFormat.parse("2018 07 11");
        dueDate = dateFormat.parse("2018 07 12");
        Order orderB = new Order("Replacing the headlamp", customerB, mechanicA, startDate, dueDate, "700R", OrderStatus.PLANNED);

        List<Order> expectedOrders = Arrays.asList(orderA, orderB);

        controller.addCustomer(customerA);
        controller.addCustomer(customerB);
        controller.addMechanic(mechanicA);
        controller.addOrder(orderA);
        controller.addOrder(orderB);

        List<Order> actualOrders = controller.getAllOrders();
        Assert.assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void shouldUpdateOrder() throws ParseException {
        Customer customerA = new Customer("Vasya", "Ivanov", "Ivanovich", "+79270234456");
        Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");
        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date startDate = dateFormat.parse("2018 07 10");
        Date dueDate = dateFormat.parse("2018 07 11");
        Order orderA = new Order("Wheel replacement", customerA, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);

        controller.addCustomer(customerA);
        controller.addCustomer(customerB);
        controller.addMechanic(mechanicA);
        controller.addOrder(orderA);
        Assert.assertEquals(orderA, controller.getOrderById(1L));

        orderA.setDescription("Wheel replacement and Replacing the headlamp");
        orderA.setCost("1200R");
        dueDate = dateFormat.parse("2018 07 12");
        orderA.setDueDate(dueDate);
        controller.updateOrder(orderA);
        Assert.assertEquals(orderA, controller.getOrderById(1L));
        Assert.assertEquals("1200R", controller.getOrderById(1L).getCost());
    }

    @Test
    public void shouldDeleteOrder() throws ParseException {
        Customer customerA = new Customer("Vasya", "Ivanov", "Ivanovich", "+79270234456");
        Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");
        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date startDate = dateFormat.parse("2018 07 10");
        Date dueDate = dateFormat.parse("2018 07 11");
        Order orderA = new Order("Wheel replacement", customerA, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);
        startDate = dateFormat.parse("2018 07 11");
        dueDate = dateFormat.parse("2018 07 12");
        Order orderB = new Order("Replacing the headlamp", customerB, mechanicA, startDate, dueDate, "700R", OrderStatus.PLANNED);

        controller.addCustomer(customerB);
        controller.addCustomer(customerA);
        controller.addMechanic(mechanicA);
        controller.addOrder(orderA);
        controller.addOrder(orderB);

        List<Order> orders = controller.getAllOrders();
        Assert.assertEquals(2, orders.size());

        controller.deleteOrder(orderB);
        orders = controller.getAllOrders();

        Assert.assertEquals(1, orders.size());
        Assert.assertEquals("Wheel replacement", orders.get(0).getDescription());
    }

    @Test
    public void shoudGetOrdersByDescription() throws ParseException {
        Customer customerA = new Customer("Vasya", "Ivanov", "Ivanovich", "+79270234456");
        Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");
        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date startDate = dateFormat.parse("2018 07 10");
        Date dueDate = dateFormat.parse("2018 07 11");

        Order orderA = new Order("Wheel replacement", customerA, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);
        Order orderB = new Order("Replacing the headlamp", customerB, mechanicA, startDate, dueDate, "700R", OrderStatus.PLANNED);
        Order orderC = new Order("Change of oil", customerA, mechanicA, startDate, dueDate, "200R", OrderStatus.COMPLETED);
        Order orderD = new Order("Fixing the generator", customerB, mechanicA, startDate, dueDate, "1000R", OrderStatus.PLANNED);
        Order orderE = new Order("Wheel replacement", customerB, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);

        controller.addCustomer(customerB);
        controller.addCustomer(customerA);
        controller.addMechanic(mechanicA);
        controller.addOrder(orderA);
        controller.addOrder(orderB);
        controller.addOrder(orderC);
        controller.addOrder(orderD);
        controller.addOrder(orderE);

        List<Order> orders =  controller.getOrderByDescription("Change of oil");
        Assert.assertFalse(orders.isEmpty());
        for (Order order : orders){
            Assert.assertEquals("Change of oil", order.getDescription());
        }

        orders = controller.getOrderByDescription("Wheel replacement");
        Assert.assertEquals(2, orders.size());

        for (Order order : orders){
            Assert.assertEquals("Wheel replacement", order.getDescription());
        }
    }

    @Test
    public void shoudGetOrdersByStatus() throws ParseException {
        Customer customerA = new Customer("Vasya", "Ivanov", "Ivanovich", "+79270234456");
        Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");
        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date startDate = dateFormat.parse("2018 07 10");
        Date dueDate = dateFormat.parse("2018 07 11");

        Order orderA = new Order("Wheel replacement", customerA, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);
        Order orderB = new Order("Replacing the headlamp", customerB, mechanicA, startDate, dueDate, "700R", OrderStatus.PLANNED);
        Order orderC = new Order("Change of oil", customerA, mechanicA, startDate, dueDate, "200R", OrderStatus.COMPLETED);
        Order orderD = new Order("Fixing the generator", customerB, mechanicA, startDate, dueDate, "1000R", OrderStatus.ACCEPTED);
        Order orderE = new Order("Wheel replacement", customerB, mechanicA, startDate, dueDate, "500R", OrderStatus.COMPLETED);

        controller.addCustomer(customerB);
        controller.addCustomer(customerA);
        controller.addMechanic(mechanicA);
        controller.addOrder(orderA);
        controller.addOrder(orderB);
        controller.addOrder(orderC);
        controller.addOrder(orderD);
        controller.addOrder(orderE);

        List<Order> orders =  controller.getOrderByStatus(OrderStatus.PLANNED);
        Assert.assertEquals(2, orders.size());
        for (Order order : orders){
            Assert.assertEquals(OrderStatus.PLANNED, order.getOrderStatus());
        }

        orders = controller.getOrderByStatus(OrderStatus.COMPLETED);
        Assert.assertEquals(2, orders.size());
        for (Order order : orders){
            Assert.assertEquals(OrderStatus.COMPLETED, order.getOrderStatus());
        }


        orders =  controller.getOrderByStatus(OrderStatus.ACCEPTED);
        Assert.assertFalse(orders.isEmpty());
        for (Order order : orders){
            Assert.assertEquals(OrderStatus.ACCEPTED, order.getOrderStatus());
        }

    }

    @Test
    public void shoudGetOrdersByCustomer() throws ParseException {
        Customer customerA = new Customer("Vasya", "Ivanov", "Ivanovich", "+79270234456");
        Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");
        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date startDate = dateFormat.parse("2018 07 10");
        Date dueDate = dateFormat.parse("2018 07 11");

        Order orderA = new Order("Wheel replacement", customerA, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);
        Order orderB = new Order("Replacing the headlamp", customerB, mechanicA, startDate, dueDate, "700R", OrderStatus.PLANNED);
        Order orderC = new Order("Change of oil", customerA, mechanicA, startDate, dueDate, "200R", OrderStatus.COMPLETED);
        Order orderD = new Order("Fixing the generator", customerB, mechanicA, startDate, dueDate, "1000R", OrderStatus.ACCEPTED);
        Order orderE = new Order("Wheel replacement", customerB, mechanicA, startDate, dueDate, "500R", OrderStatus.COMPLETED);

        controller.addCustomer(customerB);
        controller.addCustomer(customerA);
        controller.addMechanic(mechanicA);
        controller.addOrder(orderA);
        controller.addOrder(orderB);
        controller.addOrder(orderC);
        controller.addOrder(orderD);
        controller.addOrder(orderE);

        List<Order> orders =  controller.getOrderByCustomer(customerA);
        Assert.assertEquals(2, orders.size());

        orders = controller.getOrderByCustomer(customerB);
        Assert.assertEquals(3, orders.size());
    }

    @Test
    public void shoudGetOrdersByMechanic() throws ParseException {
        Customer customerA = new Customer("Vasya", "Ivanov", "Ivanovich", "+79270234456");

        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        Mechanic mechanicB = new Mechanic("Boris", "Ivanov", "Gennadevich", "200R");
        Mechanic mechanicC = new Mechanic("Gleb", "Sidorov", "Ivanovich", "230R");

        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date startDate = dateFormat.parse("2018 07 10");
        Date dueDate = dateFormat.parse("2018 07 11");

        Order orderA = new Order("Wheel replacement", customerA, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);
        Order orderB = new Order("Replacing the headlamp", customerA, mechanicB, startDate, dueDate, "700R", OrderStatus.PLANNED);
        Order orderC = new Order("Change of oil", customerA, mechanicC, startDate, dueDate, "200R", OrderStatus.COMPLETED);
        Order orderD = new Order("Fixing the generator", customerA, mechanicA, startDate, dueDate, "1000R", OrderStatus.ACCEPTED);
        Order orderE = new Order("Wheel replacement", customerA, mechanicC, startDate, dueDate, "500R", OrderStatus.COMPLETED);
        Order orderF = new Order("Change of oil", customerA, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);

        controller.addCustomer(customerA);

        controller.addMechanic(mechanicA);
        controller.addMechanic(mechanicB);
        controller.addMechanic(mechanicC);

        controller.addOrder(orderA);
        controller.addOrder(orderB);
        controller.addOrder(orderC);
        controller.addOrder(orderD);
        controller.addOrder(orderE);
        controller.addOrder(orderF);

        controller.deleteMechanic(mechanicA);
        controller.deleteMechanic(mechanicB);
        controller.deleteMechanic(mechanicC);

        controller.getAllOrders();

        List<Order> orders =  controller.getOrderByMechanic(mechanicA);
        Assert.assertEquals(3, orders.size());
        orders =  controller.getOrderByMechanic(mechanicB);
        Assert.assertEquals(1, orders.size());
        orders =  controller.getOrderByMechanic(mechanicC);
        Assert.assertEquals(2, orders.size());
    }
}
