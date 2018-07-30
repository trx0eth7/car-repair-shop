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
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@SpringComponent
@Title(CustomerAddingWindow.DEFAULT_TITLE)
public class CustomerAddingWindow extends ModalPopupWindow {

    public static final String DEFAULT_TITLE = "New Customer";

    @PropertyId("firstName")
    private TextField firstName = new TextField("First Name");
    @PropertyId("lastName")
    private TextField lastName = new TextField("Last Name");
    @PropertyId("fatherName")
    private TextField fatherName = new TextField("Father Name");
    @PropertyId("phone")
    private TextField phone = new TextField("Phone");

    private Button createBtn = new Button("Create");
    private Button cancelBtn = new Button("Cancel");

    private final Logger logger = Logger.getLogger(CustomerAddingWindow.class);
    private final BeanFieldGroup binder = new BeanFieldGroup<>(Customer.class);

    @Autowired
    public CustomerAddingWindow(WebService service) {
        super(service);
        addValidators();
        addButtonListeners();
        addContent();
    }

    private void addValidators() {
        firstName.setRequired(true);
        firstName.addValidator(new StringLengthValidator("Must be not empty"));
        firstName.setNullRepresentation("");

        lastName.setRequired(true);
        lastName.addValidator(new StringLengthValidator("Must be not empty"));
        lastName.setNullRepresentation("");

        fatherName.setNullRepresentation("");

        phone.setRequired(true);
        phone.setInputPrompt("+79999999999");
        phone.addValidator(new PhoneValidator("Please enter a valid phone number", CountryCode.RU));
        phone.setNullRepresentation("");
    }

    private void addButtonListeners() {
        createBtn.addClickListener((Button.ClickListener) clickEvent -> addCustomer());
        cancelBtn.addClickListener((Button.ClickListener) clickEvent -> close());
    }

    private void addCustomer() {
        if (binder.isValid()) {
            try {
                binder.commit();
                Customer customer = (Customer) binder.getItemDataSource().getBean();
                service.controller().addCustomer(customer);
                showSuccessNotification("Customer was added");
                close();
            } catch (FieldGroup.CommitException e) {
                logger.error("New Customer was not created", e);
                showFailureNotification("Customer was not added! Try it in a few minutes...");
            }
        } else {
            firstName.setRequiredError("First Name must be not empty");
            lastName.setRequiredError("Last Name must be not empty");
            phone.setRequiredError("Phone number must be not empty");
        }
    }

    private void addContent() {
        form.addComponents(firstName, lastName, fatherName, phone);
        HorizontalLayout actionButtonsLayout = new HorizontalLayout();
        actionButtonsLayout.addComponents(createBtn, cancelBtn);
        form.addComponent(actionButtonsLayout);

        BeanItem<Customer> item = new BeanItem<>(new Customer());
        binder.setItemDataSource(item);
        binder.bindMemberFields(this);
    }
}

