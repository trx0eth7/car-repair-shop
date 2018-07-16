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

@Title(EditingMechanicWindow.DEFAULT_TITLE)
public class EditingMechanicWindow extends ModalPopupWindow {
    public static final String DEFAULT_TITLE = "Edit Mechanic";

    @PropertyId("firstName")
    private TextField firstName = new TextField("First Name");
    @PropertyId("lastName")
    private TextField lastName = new TextField("Last Name");
    @PropertyId("fatherName")
    private TextField fatherName = new TextField("Father Name");
    @PropertyId("hourlyPay")
    private TextField hourlyPay = new TextField("Pay per hour");

    private Button saveBtn = new Button("Save");
    private Button cancelBtn = new Button("Cancel");


    public EditingMechanicWindow(Mechanic mechanicSelected) {
        super();

        firstName.setRequired(true);
        firstName.addValidator(new StringLengthValidator("Must be not empty"));
        firstName.setNullRepresentation("");

        fatherName.setNullRepresentation("");

        lastName.setRequired(true);
        lastName.addValidator(new StringLengthValidator("Must be not empty"));
        lastName.setNullRepresentation("");

        hourlyPay.setRequired(true);
        hourlyPay.addValidator(new RegexpValidator("[0-9]*", "Error"));
        hourlyPay.setNullRepresentation("");

        form.addComponents(firstName, lastName, fatherName, hourlyPay);
        final BeanFieldGroup<Mechanic> binder =
                new BeanFieldGroup<>(Mechanic.class);
        BeanItem<Mechanic> item = new BeanItem<>(mechanicSelected);
        binder.setItemDataSource(item);
        binder.buildAndBindMemberFields(this);

        HorizontalLayout actionButtonsLayout = new HorizontalLayout();

        saveBtn.addClickListener((com.vaadin.ui.Button.ClickListener) clickEvent -> {
            try {
                if (binder.isValid()) {
                    binder.commit();
                    Mechanic mechanic = binder.getItemDataSource().getBean();
                    WebService.getInstance().controller().updateMechanic(mechanic);
                    showSuccessNotification("Mechanic was edited");
                    close();
                } else {
                    firstName.setRequiredError("Give First Name");
                    lastName.setRequiredError("Give Last Name");
                    hourlyPay.setRequiredError("Give Pay per Hour");
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

