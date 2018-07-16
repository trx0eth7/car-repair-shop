package com.trx0eth7.projects.view;

import com.trx0eth7.projects.model.OrderStatus;
import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import com.trx0eth7.projects.view.components.MechanicTabContent;
import com.trx0eth7.projects.view.components.OrderTabContent;
import com.trx0eth7.projects.view.services.WebService;
import com.trx0eth7.projects.view.components.CustomerTabContent;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Theme(ValoTheme.THEME_NAME)
public class CarRepairShopUI extends UI {

    private WebService service;
    private TabSheet navigationTabSheet = new TabSheet();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        service = WebService.getInstance();
        generateTestData();
        addNavigationTabs();

        Panel border = new Panel(navigationTabSheet);
        border.setSizeFull();

        VerticalLayout rootContentLayout = new VerticalLayout();
        rootContentLayout.addComponent(border);
        setContent(rootContentLayout);

    }

    private void addNavigationTabs() {
        navigationTabSheet.setSizeFull();
        navigationTabSheet.addTab(new CustomerTabContent(), "Customers");
        navigationTabSheet.addTab(new MechanicTabContent(), "Mechanics");
        navigationTabSheet.addTab(new OrderTabContent(),"Orders");
    }

    private void generateTestData() {
        Customer customerA = new Customer("Vasya", "Ivanov", "Ivanovich", "+79270234456");
        Customer customerB = new Customer("Vasya", "Sidorov", "Michaylovich", "+79179797322");
        Customer customerC = new Customer("Vadim", "Vnukov", "Ivanovich", "+79202398637");
        Customer customerD = new Customer("Vasya", "Sidorov", "Michaylovich", "+79372393445");
        Customer customerE = new Customer("Alina", "Pivovar", "Valerevna", "+79179790014");

        Mechanic mechanicA = new Mechanic("Alex", "Petrov", "Valerevich", "150R");
        Mechanic mechanicB = new Mechanic("Boris", "Ivanov", "Gennadevich", "200R");
        Mechanic mechanicC = new Mechanic("Gleb", "Sidorov", "Ivanovich", "230R");

        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date startDate = null;
        Date dueDate = null;
        try {
            startDate = dateFormat.parse("2018 07 10");
            dueDate = dateFormat.parse("2018 07 11");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Order orderA = new Order("Wheel replacement", customerA, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);
        Order orderB = new Order("Replacing the headlamp", customerB, mechanicB, startDate, dueDate, "700R", OrderStatus.PLANNED);
        Order orderC = new Order("Change of oil", customerC, mechanicC, startDate, dueDate, "200R", OrderStatus.COMPLETED);
        Order orderD = new Order("Fixing the generator", customerD, mechanicA, startDate, dueDate, "1000R", OrderStatus.ACCEPTED);
        Order orderE = new Order("Wheel replacement", customerB, mechanicC, startDate, dueDate, "500R", OrderStatus.COMPLETED);
        Order orderF = new Order("Change of oil", customerC, mechanicA, startDate, dueDate, "500R", OrderStatus.PLANNED);



        service.controller().addCustomer(customerA);
        service.controller().addCustomer(customerB);
        service.controller().addCustomer(customerC);
        service.controller().addCustomer(customerD);
        service.controller().addCustomer(customerE);

        service.controller().addMechanic(mechanicA);
        service.controller().addMechanic(mechanicB);
        service.controller().addMechanic(mechanicC);

        service.controller().addOrder(orderA);
        service.controller().addOrder(orderB);
        service.controller().addOrder(orderC);
        service.controller().addOrder(orderD);
        service.controller().addOrder(orderE);
        service.controller().addOrder(orderF);
    }
}
