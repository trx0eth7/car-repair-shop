package com.trx0eth7.projects.view.components.tabs;

import com.trx0eth7.projects.model.entity.Order;
import com.trx0eth7.projects.view.WebService;
import com.trx0eth7.projects.view.components.windows.OrderAddingWindow;
import com.trx0eth7.projects.view.components.windows.OrderEditingWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.SelectionEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@UIScope
@SpringComponent
@Theme(ValoTheme.PANEL_BORDERLESS)
public class OrderTabContent extends BaseTabContent implements SelectionEvent.SelectionListener {

    private final WebService service;
    private Grid ordersData = new Grid("Orders Data");
    private Button addBtn = new Button("Add");
    private Button editBtn = new Button("Edit");
    private Button deleteBtn = new Button("Delete");

    private BeanItemContainer<Order> beanItemContainer;
    private Pattern patternFilter = Pattern.compile("(description|customer|orderStatus)", Pattern.CASE_INSENSITIVE);
    private List<Order> selectedOrders = new ArrayList<>();

    @Autowired
    public OrderTabContent(WebService service) {
        this.service = service;
        configureOrdersGrid();
        updateDataOrders();
        addGridFiltering();
        addButtonListeners();
        addRootContent();
    }

    private void configureOrdersGrid() {
        ordersData.setSizeFull();
        ordersData.setSelectionMode(Grid.SelectionMode.MULTI);
        ordersData.addSelectionListener(this);
        ordersData.setColumns("description", "customer", "mechanic", "startDate", "dueDate", "orderStatus", "cost");
    }

    private void addButtonListeners() {
        addBtn.addClickListener((Button.ClickListener) clickEvent -> addOrder());
        deleteBtn.addClickListener((Button.ClickListener) clickEvent -> deleteOrders());
        editBtn.addClickListener((Button.ClickListener) clickEvent -> editOrder());
    }

    private void addRootContent() {
        VerticalLayout rootContent = new VerticalLayout();

        CssLayout buttonsContent = new CssLayout();
        buttonsContent.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        buttonsContent.addComponents(addBtn, editBtn, deleteBtn);

        rootContent.addComponent(buttonsContent);
        rootContent.setMargin(true);
        rootContent.setSpacing(true);
        rootContent.addComponent(ordersData);

        setContent(rootContent);
    }

    private void addGridFiltering() {
        Grid.HeaderRow filterRow = ordersData.appendHeaderRow();
        for (Object pid : ordersData.getContainerDataSource().getContainerPropertyIds()) {
            if (patternFilter.matcher((CharSequence) pid).matches()) {
                Grid.HeaderCell cell = filterRow.getCell(pid);
                TextField filterField = new TextField();
                filterField.addStyleName(ValoTheme.TEXTFIELD_TINY);
                filterField.addTextChangeListener(textChangeEvent -> applyFilter(textChangeEvent, pid));
                cell.setComponent(filterField);
            }

        }
    }

    private void applyFilter(FieldEvents.TextChangeEvent textChangeEvent, Object pid) {
        beanItemContainer.removeContainerFilters(pid);
        if (!textChangeEvent.getText().isEmpty()) {
            beanItemContainer.addContainerFilter(
                    new SimpleStringFilter(pid, textChangeEvent.getText(), true, false));
        }
    }

    private void deleteOrders() {
        if (!selectedOrders.isEmpty()) {
            ConfirmWindow confirmWindow = new ConfirmWindow();
            UI.getCurrent().addWindow(confirmWindow);
            confirmWindow.addCloseListener(closeEvent -> {
                if (confirmWindow.isConfirm()) {
                    service.controller().deleteOrders(selectedOrders);
                    updateDataOrders();
                    showSuccessNotification("Orders were deleted");
                }
            });
        } else {
            showWarningNotification("Please, select at least one Order");
        }
    }

    private void updateDataOrders() {
        beanItemContainer = new BeanItemContainer<>(Order.class, service.controller().getAllOrders());
        ordersData.setContainerDataSource(beanItemContainer);
    }

    private void addOrder() {
        OrderAddingWindow modalWindow = new OrderAddingWindow(service);
        UI.getCurrent().addWindow(modalWindow);
        modalWindow.addCloseListener(closeEvent -> updateDataOrders());
    }

    private void editOrder() {
        if (!selectedOrders.isEmpty()) {
            if (selectedOrders.size() == 1) {
                OrderEditingWindow modalWindow = new OrderEditingWindow(selectedOrders.get(0), service);
                UI.getCurrent().addWindow(modalWindow);
                modalWindow.addCloseListener(closeEvent -> {
                    updateDataOrders();
                    selectedOrders.clear();
                });
                ordersData.deselectAll();
            } else {
                showWarningNotification("Please, select only one Order");
            }
        } else {
            showWarningNotification("Please, select a Order");
        }
    }

    @Override
    public void select(SelectionEvent event) {
        selectedOrders.clear();
        for (Object item : ordersData.getSelectionModel().getSelectedRows()) {
            selectedOrders.add((Order) item);
        }
    }
}
