package com.trx0eth7.projects.view.components;

import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.view.components.windows.AddingCustomerWindow;
import com.trx0eth7.projects.view.components.windows.EditingCustomerWindow;
import com.trx0eth7.projects.view.services.WebService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Collection;

public class CustomerTabContent extends Panel {

    private Grid customersGrid = new Grid("Customers Data");
    private WebService service = WebService.getInstance();
    private Button addBtn = new Button("Add");
    private Button editBtn = new Button("Edit");
    private Button removeBtn = new Button("Remove");

    public CustomerTabContent() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        initUIComponents();
    }

    private void initUIComponents() {
        final VerticalLayout rootContentLayout = new VerticalLayout();
        addBtn.addClickListener((Button.ClickListener) clickEvent -> openWindowNewCustomer());
        removeBtn.addClickListener((Button.ClickListener) clickEvent -> removeCustomers());
        editBtn.addClickListener((Button.ClickListener) clickEvent -> openWindowEditCustomer());
        CssLayout buttonsLayout = new CssLayout();
        buttonsLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        buttonsLayout.addComponents(addBtn, editBtn, removeBtn);
        rootContentLayout.addComponent(buttonsLayout);
        rootContentLayout.setMargin(true);
        rootContentLayout.setSpacing(true);

        rootContentLayout.addComponent(customersGrid);

        updateDataCustomers();

        customersGrid.setSizeFull();
        customersGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        customersGrid.setColumnOrder("id", "firstName", "lastName", "fatherName", "phone");
        setContent(rootContentLayout);

    }

    private void removeCustomers() {
        Collection<Object> items = customersGrid.getSelectedRows();
        for (Object item : items) {
            Customer customer = (Customer) item;
            service.controller().deleteCustomer(customer);
        }
        if (items.isEmpty()) {
            showFailureNotification("Please, choose customer for edit");
        } else {
            showSuccessNotification("Customers was removed");
            updateDataCustomers();
        }
    }

    private void updateDataCustomers() {
        final BeanItemContainer<Customer> beanItemContainer =
                new BeanItemContainer<>(Customer.class, service.controller().getAllCustomers());
        customersGrid.setContainerDataSource(beanItemContainer);
    }

    private void openWindowNewCustomer() {
        AddingCustomerWindow modalWindow = new AddingCustomerWindow();
        UI.getCurrent().addWindow(modalWindow);
        modalWindow.addCloseListener(closeEvent -> updateDataCustomers());
    }

    private void openWindowEditCustomer() {
        if(customersGrid.getSelectedRows().size() > 1) {
            showFailureNotification("Please, choose once customer for edit");
        } else {
            Customer customerSelected = null;
            for (Object item : customersGrid.getSelectedRows()) {
                customerSelected = (Customer) item;
            }

            if (customerSelected == null) {
                showFailureNotification("Please, choose customer for edit");
            } else {
                EditingCustomerWindow modalWindow = new EditingCustomerWindow(customerSelected);
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

}
