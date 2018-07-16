package com.trx0eth7.projects.view.components;

import com.trx0eth7.projects.model.entity.Order;
import com.trx0eth7.projects.view.components.windows.AddingOrderWindow;
import com.trx0eth7.projects.view.components.windows.EditingOrderWindow;
import com.trx0eth7.projects.view.services.WebService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Collection;

public class OrderTabContent extends Panel {

    private Grid orderGrid = new Grid("Orders Data");
    private WebService service = WebService.getInstance();
    private Button addBtn = new Button("Add");
    private Button editBtn = new Button("Edit");
    private Button removeBtn = new Button("Remove");

    public OrderTabContent() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        initUIComponents();
    }

    private void initUIComponents() {
        final VerticalLayout rootContentLayout = new VerticalLayout();
        addBtn.addClickListener((Button.ClickListener) clickEvent -> openWindowNewOrder());
        removeBtn.addClickListener((Button.ClickListener) clickEvent -> removeOrders());
        editBtn.addClickListener((Button.ClickListener) clickEvent -> openWindowEditOrder());
        CssLayout buttonsLayout = new CssLayout();
        buttonsLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        buttonsLayout.addComponents(addBtn, editBtn, removeBtn);
        rootContentLayout.addComponent(buttonsLayout);
        rootContentLayout.setMargin(true);
        rootContentLayout.setSpacing(true);

        rootContentLayout.addComponent(orderGrid);

        updateDataOrders();

        orderGrid.setSizeFull();
        orderGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        orderGrid.setColumnOrder("id", "description", "customer",
                "mechanic", "startDate", "dueDate", "cost", "orderStatus");
        setContent(rootContentLayout);

    }

    private void removeOrders() {
        Collection<Object> items = orderGrid.getSelectedRows();
        for (Object item: items) {
            Order order = (Order) item;
            service.controller().deleteOrder(order);
        }
        if (items.isEmpty()) {
            showFailureNotification("Please, choose order for edit");
        } else {
            showSuccessNotification("Orders was removed");
            updateDataOrders();
        }
    }

    private void updateDataOrders(){
        final BeanItemContainer<Order> beanItemContainer =
                new BeanItemContainer<>(Order.class, service.controller().getAllOrders());
        orderGrid.setContainerDataSource(beanItemContainer);
    }

    private void openWindowNewOrder() {
        AddingOrderWindow modalWindow = new AddingOrderWindow();
        UI.getCurrent().addWindow(modalWindow);
        modalWindow.addCloseListener(closeEvent -> updateDataOrders());
    }

    private void openWindowEditOrder() {
        if(orderGrid.getSelectedRows().size() > 1) {
            showFailureNotification("Please, choose once order for edit");
        } else {
            Order orderSelected = null;
            for (Object item : orderGrid.getSelectedRows()) {
                orderSelected = (Order) item;
            }

            if (orderSelected == null) {
                showFailureNotification("Please, choose order for edit");
            } else {
                EditingOrderWindow modalWindow = new EditingOrderWindow(orderSelected);
                UI.getCurrent().addWindow(modalWindow);
                modalWindow.addCloseListener(closeEvent -> updateDataOrders());
            }
        }
    }

    private void showFailureNotification(String caption){
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
        notification.show(getUI().getPage());
    }

    private void showSuccessNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
        notification.show(getUI().getPage());
    }

}
