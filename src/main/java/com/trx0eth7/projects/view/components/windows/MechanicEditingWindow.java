package com.trx0eth7.projects.view.components.windows;

import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.view.WebService;
import com.vaadin.annotations.Title;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.IntegerRangeValidator;
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
@Title(MechanicEditingWindow.DEFAULT_TITLE)
public class MechanicEditingWindow extends ModalPopupWindow {
    public static final String DEFAULT_TITLE = "Edit Mechanic";

    @PropertyId("firstName")
    private TextField firstName = new TextField("First Name");
    @PropertyId("lastName")
    private TextField lastName = new TextField("Last Name");
    @PropertyId("fatherName")
    private TextField fatherName = new TextField("Father Name");
    @PropertyId("payPerHour")
    private TextField payPerHour = new TextField("Pay per hour");

    private Button saveBtn = new Button("Save");
    private Button cancelBtn = new Button("Cancel");

    private final Logger logger = Logger.getLogger(MechanicEditingWindow.class);
    private final Mechanic mechanicSelected;
    private final BeanFieldGroup binder = new BeanFieldGroup<>(Mechanic.class);

    public MechanicEditingWindow(Mechanic mechanicSelected, WebService service) {
        super(service);
        this.mechanicSelected = mechanicSelected;
        addValidators();
        addButtonListeners();
        addContent();
    }

    private void addValidators() {
        firstName.setRequired(true);
        firstName.addValidator(new StringLengthValidator("Must not be empty"));
        firstName.addValidator(new RegexpValidator("^[^\\d]+$", "First Name cannot contains number"));
        firstName.setNullRepresentation("");

        fatherName.setNullRepresentation("");

        lastName.setRequired(true);
        lastName.addValidator(new StringLengthValidator("Must not be empty"));
        lastName.addValidator(new RegexpValidator("^[^\\d]+$", "Last Name cannot contains number"));
        lastName.setNullRepresentation("");

        payPerHour.setRequired(true);
        payPerHour.addValidator(new IntegerRangeValidator("Numbers only (from 1 to 1000)", 1, 1000));
        payPerHour.setNullRepresentation("");
    }

    private void addButtonListeners() {
        saveBtn.addClickListener((Button.ClickListener) clickEvent -> updateMechanic());
        cancelBtn.addClickListener((Button.ClickListener) clickEvent -> close());
    }

    private void updateMechanic() {
        if (binder.isValid()) {
            try {
                binder.commit();
                Mechanic mechanic = (Mechanic) binder.getItemDataSource().getBean();
                service.controller().updateMechanic(mechanic);
                showSuccessNotification("Mechanic was edited");
                close();
            } catch (FieldGroup.CommitException e) {
                logger.error("Mechanic was not updated", e);
                showFailureNotification("Mechanic was not edited! Try it in a few minutes...");
            }
        } else {

            firstName.setRequiredError("First Name must not be empty");
            lastName.setRequiredError("Last Name must not be empty");
            payPerHour.setRequiredError("Pay per Hour must not be empty");
        }
    }

    private void addContent() {
        form.addComponents(firstName, lastName, fatherName, payPerHour);
        HorizontalLayout actionButtonsLayout = new HorizontalLayout();
        actionButtonsLayout.addComponents(saveBtn, cancelBtn);
        form.addComponent(actionButtonsLayout);

        BeanItem<Mechanic> item = new BeanItem<>(mechanicSelected);
        binder.setItemDataSource(item);
        binder.bindMemberFields(this);

    }
}

