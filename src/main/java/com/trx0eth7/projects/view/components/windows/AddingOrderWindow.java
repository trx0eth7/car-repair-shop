package com.trx0eth7.projects.view.components.windows;

import com.trx0eth7.projects.model.OrderStatus;
import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import com.trx0eth7.projects.view.services.WebService;
import com.vaadin.annotations.Title;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

@Title(AddingOrderWindow.DEFAULT_TITLE)
public class AddingOrderWindow extends ModalPopupWindow {

    public static final String DEFAULT_TITLE = "New Order";

    @PropertyId("description")
    private TextArea description = new TextArea("Description");
    @PropertyId("customer")
    private ComboBox customer = new ComboBox("Customers");
    @PropertyId("mechanic")
    private ComboBox mechanic = new ComboBox("Mechanics");
    @PropertyId("startDate")
    private DateField startDate = new DateField("Start Date");
    @PropertyId("dueDate")
    private DateField dueDate = new DateField("Due Date");
    @PropertyId("cost")
    private TextField cost = new TextField("Cost");
    @PropertyId("orderStatus")
    private ComboBox orderStatus = new ComboBox("Order Status");

    private Button createBtn = new Button("Create");
    private Button cancelBtn = new Button("Cancel");

    public AddingOrderWindow() {
        WebService service = WebService.getInstance();

        description.setRequired(true);
        description.addValidator(new StringLengthValidator("Must be not empty"));
        description.setNullRepresentation("");

        customer.setContainerDataSource(
                new BeanItemContainer<>(Customer.class, service.controller().getAllCustomers()));
        customer.setRequired(true);
        mechanic.setContainerDataSource(
                new BeanItemContainer<>(Mechanic.class, service.controller().getAllMechanics()));
        mechanic.setRequired(true);
        startDate.setRequired(true);
        dueDate.setRequired(true);
        cost.setRequired(true);
        cost.setNullRepresentation("");
        cost.addValidator(new StringLengthValidator("Must be not empty"));
        orderStatus.addItems(OrderStatus.PLANNED, OrderStatus.COMPLETED, OrderStatus.ACCEPTED);
        orderStatus.setRequired(true);

        form.addComponents(description, customer, mechanic, startDate, dueDate, orderStatus, cost);

        final BeanFieldGroup<Order> binder =
                new BeanFieldGroup<>(Order.class);
        BeanItem<Order> item = new BeanItem<>(new Order());
        binder.setItemDataSource(item);
        binder.buildAndBindMemberFields(this);

        HorizontalLayout actionButtonsLayout = new HorizontalLayout();

        createBtn.addClickListener((Button.ClickListener) clickEvent -> {
            try {
                if (binder.isValid()) {
                    binder.commit();
                    Order order = binder.getItemDataSource().getBean();
                    WebService.getInstance().controller().addOrder(order);
                    showSuccessNotification("Order was added");
                    close();
                } else {
                    description.setRequiredError("Give Description");
                    customer.setRequiredError("Please, select customer");
                    mechanic.setRequiredError("Please, select mechanic");
                    startDate.setRequiredError("Please, specify start date");
                    dueDate.setRequiredError("Please, specify due date");
                    cost.setRequiredError("Give Cost");
                    orderStatus.setRequiredError("Please, select order status");
                }
            } catch (FieldGroup.CommitException e) {
                e.printStackTrace();
            }
        });
        cancelBtn.addClickListener((Button.ClickListener) clickEvent -> close());
        actionButtonsLayout.addComponents(createBtn, cancelBtn);
        form.addComponent(actionButtonsLayout);

    }
}
