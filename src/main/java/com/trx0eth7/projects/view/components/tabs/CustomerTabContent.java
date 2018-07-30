package com.trx0eth7.projects.view.components.tabs;

import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.view.WebService;
import com.trx0eth7.projects.view.components.windows.CustomerAddingWindow;
import com.trx0eth7.projects.view.components.windows.CustomerEditingWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@UIScope
@SpringComponent
@Theme(ValoTheme.PANEL_BORDERLESS)
public class CustomerTabContent extends Panel {

    private final WebService service;
    private Grid customersGrid = new Grid("Customers Data");
    private Button addBtn = new Button("Add");
    private Button editBtn = new Button("Edit");
    private Button removeBtn = new Button("Remove");

    @Autowired
    public CustomerTabContent(WebService service) {
        this.service = service;

        configureCustomersGrid();
        updateDataCustomers();
        addButtonListeners();
        addRootContent();
    }

    private void configureCustomersGrid() {
        customersGrid.setSizeFull();
        customersGrid.setSelectionMode(Grid.SelectionMode.MULTI);
    }

    private void addButtonListeners() {
        addBtn.addClickListener((Button.ClickListener) clickEvent -> addCustomer());
        removeBtn.addClickListener((Button.ClickListener) clickEvent -> removeCustomers());
        editBtn.addClickListener((Button.ClickListener) clickEvent -> openWindowEditCustomer());
    }

    private void addRootContent() {
        VerticalLayout rootContent = new VerticalLayout();

        CssLayout buttonsContent = new CssLayout();
        buttonsContent.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        buttonsContent.addComponents(addBtn, editBtn, removeBtn);

        rootContent.addComponent(buttonsContent);
        rootContent.setMargin(true);
        rootContent.setSpacing(true);
        rootContent.addComponent(customersGrid);

        setContent(rootContent);

    }

    private void removeCustomers() {
        Collection<Object> items = customersGrid.getSelectedRows();
        for (Object item : items) {
            Customer customer = (Customer) item;
            //TODO: Система должна иметь защиту на уровне БД от удаления клиента и механика, для которых существуют заказы
            if (service.controller().getOrderByCustomer(customer).isEmpty()) {
                service.controller().deleteCustomer(customer);
            } else {
                showWarningNotification(String.format("Customer bind order"));
            }
        }
        if (items.isEmpty()) {
            showFailureNotification("Please, choose customer for edit");
        } else {
            showSuccessNotification("Customers was removed");
            updateDataCustomers();
        }
    }

    private void updateDataCustomers() {
        BeanItemContainer<Customer> beanItemContainer =
                new BeanItemContainer<>(Customer.class, service.controller().getAllCustomers());
        customersGrid.setContainerDataSource(beanItemContainer);
    }

    private void addCustomer() {
        CustomerAddingWindow window = new CustomerAddingWindow(service);
        UI.getCurrent().addWindow(window);
        window.addCloseListener(closeEvent -> updateDataCustomers());
    }

    private void openWindowEditCustomer() {
        if (customersGrid.getSelectedRows().size() > 1) {
            showFailureNotification("Please, choose once customer for edit");
        } else {
            Customer customerSelected = null;
            for (Object item : customersGrid.getSelectedRows()) {
                customerSelected = (Customer) item;
            }

            if (customerSelected == null) {
                showFailureNotification("Please, choose customer for edit");
            } else {
                CustomerEditingWindow modalWindow = new CustomerEditingWindow(customerSelected, service);
                UI.getCurrent().addWindow(modalWindow);
                modalWindow.addCloseListener(closeEvent -> updateDataCustomers());
            }
        }
    }

    private void showSuccessNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
        notification.show(getUI().getPage());
    }

    private void showFailureNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
        notification.show(getUI().getPage());
    }

    private void showWarningNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_WARNING);
        notification.show(getUI().getPage());
    }

}
