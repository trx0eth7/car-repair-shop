package com.trx0eth7.projects.view.components.windows;

import com.trx0eth7.projects.model.entity.Customer;
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
@Title(MechanicAddingWindow.DEFAULT_TITLE)
public class MechanicAddingWindow extends ModalPopupWindow {

    public static final String DEFAULT_TITLE = "New Mechanic";

    @PropertyId("firstName")
    private TextField firstName = new TextField("First Name");
    @PropertyId("lastName")
    private TextField lastName = new TextField("Last Name");
    @PropertyId("fatherName")
    private TextField fatherName = new TextField("Father Name");
    @PropertyId("payPerHour")
    private TextField payPerHour = new TextField("Pay per hour");

    private Button createBtn = new Button("Create");
    private Button cancelBtn = new Button("Cancel");

    private final Logger logger = Logger.getLogger(CustomerAddingWindow.class);
    private final BeanFieldGroup binder = new BeanFieldGroup<>(Customer.class);

    public MechanicAddingWindow(WebService service) {
        super(service);
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

        payPerHour.setRequired(true);
        payPerHour.addValidator(new IntegerRangeValidator("Numbers only (from 1 to 1000)", 1, 1000));
        payPerHour.setNullRepresentation("");
    }

    private void addButtonListeners() {
        createBtn.addClickListener((Button.ClickListener) clickEvent -> addMechanic());
        cancelBtn.addClickListener((Button.ClickListener) clickEvent -> close());
    }

    private void addMechanic() {
        if (binder.isValid()) {
            try {
                binder.commit();
                Mechanic mechanic = (Mechanic) binder.getItemDataSource().getBean();
                service.controller().addMechanic(mechanic);
                showSuccessNotification("Mechanic was added");
                close();
            } catch (FieldGroup.CommitException e) {
                logger.error("New Mechanic was not created", e);
                showFailureNotification("Mechanic was not added! Try it in a few minutes...");
            }
        } else {
            firstName.setRequiredError("First Name must not be empty");
            lastName.setRequiredError("Last Name must not be empty");
            payPerHour.setRequiredError("Pay per hour must not be empty");
        }
    }

    private void addContent() {
        form.addComponents(firstName, lastName, fatherName, payPerHour);
        HorizontalLayout actionButtonsLayout = new HorizontalLayout();
        actionButtonsLayout.addComponents(createBtn, cancelBtn);
        form.addComponent(actionButtonsLayout);

        BeanItem<Mechanic> item = new BeanItem<>(new Mechanic());
        binder.setItemDataSource(item);
        binder.bindMemberFields(this);
    }

}
