package com.trx0eth7.projects.view.components.windows;

import com.trx0eth7.projects.model.entity.Customer;
import com.trx0eth7.projects.view.components.validators.CountryCode;
import com.trx0eth7.projects.view.components.validators.PhoneValidator;
import com.trx0eth7.projects.view.services.WebService;
import com.vaadin.annotations.Title;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

@Title(EditingCustomerWindow.DEFAULT_TITLE)
public class EditingCustomerWindow extends ModalPopupWindow {
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

    public EditingCustomerWindow(Customer customerSelected) {
        super();

        firstName.setRequired(true);
        firstName.addValidator(new StringLengthValidator("Must be not empty"));
        firstName.setNullRepresentation("");


        fatherName.setNullRepresentation("");

        lastName.setRequired(true);
        lastName.addValidator(new StringLengthValidator("Must be not empty"));
        lastName.setNullRepresentation("");

        phone.setRequired(true);
        phone.setInputPrompt("+7(927)02-312-69");
        phone.addValidator(new PhoneValidator("Please enter a valid phone number.", CountryCode.RU));
        phone.setNullRepresentation("");



        form.addComponents(firstName, lastName, fatherName, phone);
        final BeanFieldGroup<Customer> binder =
                new BeanFieldGroup<>(Customer.class);
        BeanItem<Customer> item = new BeanItem<>(customerSelected);
        binder.setItemDataSource(item);
        binder.buildAndBindMemberFields(this);

        HorizontalLayout actionButtonsLayout = new HorizontalLayout();

        saveBtn.addClickListener((com.vaadin.ui.Button.ClickListener) clickEvent -> {
            try {
                if (binder.isValid()) {
                    binder.commit();
                    Customer customer = binder.getItemDataSource().getBean();
                    WebService.getInstance().controller().updateCustomer(customer);
                    showSuccessNotification("Customer was edited");
                    close();
                } else {
                    firstName.setRequiredError("Give First Name");
                    lastName.setRequiredError("Give Last Name");
                    phone.setRequiredError("Give Phone (Number)");
                }
            } catch (FieldGroup.CommitException e) {
                e.printStackTrace();
            }
        });
        cancelBtn.addClickListener((com.vaadin.ui.Button.ClickListener) clickEvent -> close());
        actionButtonsLayout.addComponents(saveBtn, cancelBtn);
        form.addComponent(actionButtonsLayout);
    }
}
