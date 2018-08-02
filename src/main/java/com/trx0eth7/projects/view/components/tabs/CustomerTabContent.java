package com.trx0eth7.projects.view.components.tabs;

import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Order;
import com.trx0eth7.projects.view.WebService;
import com.trx0eth7.projects.view.components.windows.CustomerAddingWindow;
import com.trx0eth7.projects.view.components.windows.CustomerEditingWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringComponent
@Theme(ValoTheme.PANEL_BORDERLESS)
public class CustomerTabContent extends BaseTabContent implements SelectionEvent.SelectionListener {

    private final WebService service;
    private Grid customersData = new Grid("Customers Data");
    private Button addBtn = new Button("Add");
    private Button editBtn = new Button("Edit");
    private Button deleteBtn = new Button("Delete");

    private List<Customer> selectedCustomers = new ArrayList<>();

    @Autowired
    public CustomerTabContent(WebService service) {
        this.service = service;
        configureCustomersGrid();
        updateDataCustomers();
        addButtonListeners();
        addRootContent();
    }

    private void configureCustomersGrid() {
        customersData.setSizeFull();
        customersData.setSelectionMode(Grid.SelectionMode.MULTI);
        customersData.addSelectionListener(this);
        customersData.setColumns("lastName", "firstName", "fatherName", "phone");
    }

    private void addButtonListeners() {
        addBtn.addClickListener((Button.ClickListener) clickEvent -> addCustomer());
        deleteBtn.addClickListener((Button.ClickListener) clickEvent -> deleteCustomers());
        editBtn.addClickListener((Button.ClickListener) clickEvent -> editCustomer());
    }

    private void addRootContent() {
        VerticalLayout rootContent = new VerticalLayout();

        CssLayout buttonsContent = new CssLayout();
        buttonsContent.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        buttonsContent.addComponents(addBtn, editBtn, deleteBtn);

        rootContent.addComponent(buttonsContent);
        rootContent.setMargin(true);
        rootContent.setSpacing(true);
        rootContent.addComponent(customersData);

        setContent(rootContent);

    }

    private void deleteCustomers() {
        if (!selectedCustomers.isEmpty()) {
            ConfirmWindow confirmWindow = new ConfirmWindow();
            UI.getCurrent().addWindow(confirmWindow);
            confirmWindow.addCloseListener(closeEvent -> {
                if (confirmWindow.isConfirm()) {
                    for (Customer customer : selectedCustomers) {
                        List<Order> orders = service.controller().getOrdersByCustomer(customer);
                        if (!orders.isEmpty()) {
                            showWarningNotification(String.format("%s %s has an unexecuted Order, you cannot delete him",
                                    customer.getFirstName(), customer.getLastName()));
                            return;
                        }
                    }
                    service.controller().deleteCustomers(selectedCustomers);
                    updateDataCustomers();
                    showSuccessNotification("Customers were deleted");
                }
            });
        } else {
            showWarningNotification("Please, select at least one Customer");
        }
    }

    private void updateDataCustomers() {
        BeanItemContainer<Customer> beanItemContainer =
                new BeanItemContainer<>(Customer.class, service.controller().getAllCustomers());
        customersData.setContainerDataSource(beanItemContainer);
    }

    private void addCustomer() {
        CustomerAddingWindow window = new CustomerAddingWindow(service);
        UI.getCurrent().addWindow(window);
        window.addCloseListener(closeEvent -> updateDataCustomers());
    }

    private void editCustomer() {
        if (!selectedCustomers.isEmpty()) {
            if (selectedCustomers.size() == 1) {
                CustomerEditingWindow modalWindow = new CustomerEditingWindow(selectedCustomers.get(0), service);
                UI.getCurrent().addWindow(modalWindow);
                modalWindow.addCloseListener(closeEvent -> {
                    updateDataCustomers();
                    selectedCustomers.clear();
                });
                customersData.deselectAll();
            } else {
                showWarningNotification("Please, select only one Customer");
            }
        } else {
            showWarningNotification("Please, select a Customer");
        }
    }

    @Override
    public void select(SelectionEvent event) {
        selectedCustomers.clear();
        for (Object item : customersData.getSelectionModel().getSelectedRows()) {
            selectedCustomers.add((Customer) item);
        }
    }
}
