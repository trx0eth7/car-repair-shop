package com.trx0eth7.projects.view.components.windows;

import com.trx0eth7.projects.model.OrderStatus;
import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import com.trx0eth7.projects.view.WebService;
import com.vaadin.annotations.Title;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;

@UIScope
@SpringComponent
@Title(OrderEditingWindow.DEFAULT_TITLE)
public class OrderEditingWindow extends ModalPopupWindow {
    static final String DEFAULT_TITLE = "Edit Order";

    @PropertyId("description")
    private TextArea description = new TextArea("Description");
    @PropertyId("customer")
    private ComboBox customers = new ComboBox("Customers");
    @PropertyId("mechanic")
    private ComboBox mechanics = new ComboBox("Mechanics");
    @PropertyId("startDate")
    private DateField startDate = new DateField("Start Date");
    @PropertyId("dueDate")
    private DateField dueDate = new DateField("Due Date");
    @PropertyId("cost")
    private TextField cost = new TextField("Cost");
    @PropertyId("orderStatus")
    private ComboBox orderStatus = new ComboBox("Order Status");

    private Button saveBtn = new Button("Save");
    private Button cancelBtn = new Button("Cancel");


    private final Logger logger = Logger.getLogger(OrderEditingWindow.class);
    private final Order orderSelected;
    private final BeanFieldGroup binder = new BeanFieldGroup<>(Order.class);

    public OrderEditingWindow(Order orderSelected, WebService service) {
        super(service);
        this.orderSelected = orderSelected;
        addValidators();
        addButtonListeners();
        addContent();
    }

    private void addValidators() {
        description.setRequired(true);
        description.addValidator(new StringLengthValidator("Must not be empty"));
        description.setNullRepresentation("");

        customers.setContainerDataSource(
                new BeanItemContainer<>(Customer.class, service.controller().getAllCustomers()));
        customers.setRequired(true);
        mechanics.setContainerDataSource(
                new BeanItemContainer<>(Mechanic.class, service.controller().getAllMechanics()));
        mechanics.setRequired(true);
        startDate.setRequired(true);
        dueDate.setRequired(true);
        cost.setRequired(true);
        cost.setNullRepresentation("");
        cost.addValidator(new IntegerRangeValidator("Numbers only (from 1 to 100000)", 1, 100000));
        orderStatus.addItems(OrderStatus.PLANNED, OrderStatus.COMPLETED, OrderStatus.ACCEPTED);
        orderStatus.setRequired(true);
    }

    private void addButtonListeners() {
        saveBtn.addClickListener((Button.ClickListener) clickEvent -> updateOrder());
        cancelBtn.addClickListener((Button.ClickListener) clickEvent -> close());
    }

    private void updateOrder() {
        if (binder.isValid()) {
            try {
                binder.commit();
                Order order = (Order) binder.getItemDataSource().getBean();
                if (isDateValid(order)) {
                    service.controller().updateOrder(order);
                    showSuccessNotification("Order was edited");
                    close();
                } else {
                    showFailureNotification("Due date must be after Start date");
                    dueDate.clear();
                    dueDate.setRequiredError("Please, fix due date");
                }
            } catch (FieldGroup.CommitException e) {
                logger.error("Order was not updated", e);
                showFailureNotification("Order was not edited! Try it in a few minutes...");
            }
        } else {
            description.setRequiredError("Description must not be empty");
            customers.setRequiredError("Please, select customers");
            mechanics.setRequiredError("Please, select mechanics");
            startDate.setRequiredError("Please, specify start date");
            dueDate.setRequiredError("Please, specify due date");
            cost.setRequiredError("Cost must not be empty");
            orderStatus.setRequiredError("Please, select order status");
        }
    }

    private boolean isDateValid(Order order) {
        return order.getStartDate().before(order.getDueDate()) || order.getStartDate().equals(order.getDueDate());
    }

    private void addContent() {
        form.addComponents(description, customers, mechanics, startDate, dueDate, orderStatus, cost);
        HorizontalLayout actionButtonsLayout = new HorizontalLayout();
        actionButtonsLayout.addComponents(saveBtn, cancelBtn);
        form.addComponent(actionButtonsLayout);

        BeanItem<Order> item = new BeanItem<>(orderSelected);
        binder.setItemDataSource(item);
        binder.bindMemberFields(this);
    }
}
