package com.trx0eth7.projects.view.components.windows;

import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.view.WebService;
import com.trx0eth7.projects.view.components.validators.CountryCode;
import com.trx0eth7.projects.view.components.validators.PhoneValidator;
import com.vaadin.annotations.Title;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;

@UIScope
@SpringComponent
@Title(CustomerEditingWindow.DEFAULT_TITLE)
public class CustomerEditingWindow extends ModalPopupWindow {
    public static final String DEFAULT_TITLE = "Edit Customer";

    @PropertyId("firstName")
    private TextField firstName = new TextField("First Name");
    @PropertyId("lastName")
    private TextField lastName = new TextField("Last Name");
    @PropertyId("fatherName")
    private TextField fatherName = new TextField("Father Name");
    @PropertyId("phone")
    private TextField phone = new TextField("Phone");

    private Button saveBtn = new Button("Save");
    private Button cancelBtn = new Button("Cancel");

    private final Logger logger = Logger.getLogger(Customer.class);
    private final Customer customerSelected;
    private final BeanFieldGroup binder = new BeanFieldGroup<>(Customer.class);

    public CustomerEditingWindow(Customer customerSelected, WebService service) {
        super(service);
        this.customerSelected = customerSelected;
        addValidators();
        addButtonListeners();
        addContent();
    }

    private void addValidators() {
        firstName.setRequired(true);
        firstName.addValidator(new StringLengthValidator("Must be not empty"));
        firstName.addValidator(new RegexpValidator("^[^\\d]+$", "First Name cannot contains number"));
        firstName.setNullRepresentation("");

        lastName.setRequired(true);
        lastName.addValidator(new StringLengthValidator("Must be not empty"));
        lastName.addValidator(new RegexpValidator("^[^\\d]+$", "Last Name cannot contains number"));
        lastName.setNullRepresentation("");

        fatherName.setNullRepresentation("");

        phone.setRequired(true);
        phone.setInputPrompt("+79999999999");
        phone.addValidator(new PhoneValidator("Please enter a valid phone number", CountryCode.RU));
        phone.setNullRepresentation("");
    }

    private void addButtonListeners() {
        saveBtn.addClickListener((Button.ClickListener) clickEvent -> updateCustomer());
        cancelBtn.addClickListener((Button.ClickListener) clickEvent -> close());
    }

    private void updateCustomer() {
        if (binder.isValid()) {
            try {
                binder.commit();
                Customer customer = (Customer) binder.getItemDataSource().getBean();
                service.controller().updateCustomer(customer);
                showSuccessNotification("Customer was edited");
                close();
            } catch (FieldGroup.CommitException e) {
                logger.error("Customer was not updated", e);
                showFailureNotification("Customer was not edited! Try it in a few minutes...");
            }
        } else {
            firstName.setRequiredError("First Name must not be empty");
            lastName.setRequiredError("Last Name must not be empty");
            phone.setRequiredError("Phone number must not be empty");
        }
    }

    private void addContent() {
        form.addComponents(firstName, lastName, fatherName, phone);
        HorizontalLayout actionButtonsLayout = new HorizontalLayout();
        actionButtonsLayout.addComponents(saveBtn, cancelBtn);
        form.addComponent(actionButtonsLayout);

        BeanItem<Customer> item = new BeanItem<>(customerSelected);
        binder.setItemDataSource(item);
        binder.bindMemberFields(this);
    }
}
