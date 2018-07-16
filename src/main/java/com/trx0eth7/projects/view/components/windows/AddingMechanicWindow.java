package com.trx0eth7.projects.view.components.windows;

import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.view.services.WebService;
import com.vaadin.annotations.Title;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

@Title(AddingMechanicWindow.DEFAULT_TITLE)
public class AddingMechanicWindow extends ModalPopupWindow{

    public static final String DEFAULT_TITLE = "New Mechanic";

    @PropertyId("firstName")
    private TextField firstName = new TextField("First Name");
    @PropertyId("lastName")
    private TextField lastName = new TextField("Last Name");
    @PropertyId("fatherName")
    private TextField fatherName = new TextField("Father Name");
    @PropertyId("hourlyPay")
    private TextField hourlyPay = new TextField("Pay per hour");

    private Button createBtn = new Button("Create");
    private Button cancelBtn = new Button("Cancel");

    public AddingMechanicWindow() {
        super();
        initComponentsOfWindow();
    }

    private void initComponentsOfWindow() {
        firstName.setRequired(true);
        firstName.addValidator(new StringLengthValidator("Must be not empty"));
        firstName.setNullRepresentation("");

        lastName.setRequired(true);
        lastName.addValidator(new StringLengthValidator("Must be not empty"));
        lastName.setNullRepresentation("");

        fatherName.setNullRepresentation("");

        hourlyPay.setRequired(true);
        hourlyPay.addValidator(new RegexpValidator("[0-9]*", "Error"));
        hourlyPay.setNullRepresentation("");

        form.addComponents(firstName, lastName, fatherName, hourlyPay);

        final BeanFieldGroup<Mechanic> binder =
                new BeanFieldGroup<>(Mechanic.class);
        BeanItem<Mechanic> item = new BeanItem<>(new Mechanic());
        binder.setItemDataSource(item);
        binder.buildAndBindMemberFields(this);

        HorizontalLayout actionButtonsLayout = new HorizontalLayout();

        createBtn.addClickListener((Button.ClickListener) clickEvent -> {
            try {
                if (binder.isValid()) {
                    binder.commit();
                    Mechanic mechanic = binder.getItemDataSource().getBean();
                    WebService.getInstance().controller().addMechanic(mechanic);
                    showSuccessNotification("Mechanic was added");
                    close();
                } else {
                    firstName.setRequiredError("Give First Name");
                    lastName.setRequiredError("Give Last Name");
                    hourlyPay.setRequiredError("Give Pay per hour");
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
