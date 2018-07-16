package com.trx0eth7.projects.view.components;

import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.view.components.windows.AddingMechanicWindow;
import com.trx0eth7.projects.view.components.windows.EditingMechanicWindow;
import com.trx0eth7.projects.view.components.windows.ShowingStatisticWindow;
import com.trx0eth7.projects.view.services.WebService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MechanicTabContent extends Panel {


    private Grid mechanicGrid = new Grid("Mechanics Data");
    private WebService service = WebService.getInstance();
    private Button addBtn = new Button("Add");
    private Button editBtn = new Button("Edit");
    private Button removeBtn = new Button("Remove");
    private Button showBtn = new Button("Show Statistics");

    public MechanicTabContent() {
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        initUIComponents();
    }

    private void initUIComponents() {
        final VerticalLayout rootContentLayout = new VerticalLayout();
        addBtn.addClickListener((Button.ClickListener) clickEvent -> openWindowNewMechanic());
        removeBtn.addClickListener((Button.ClickListener) clickEvent -> removeMechanics());
        editBtn.addClickListener((Button.ClickListener) clickEvent -> openWindowEditMechanic());
        showBtn.addClickListener((Button.ClickListener) clickEvent -> openWindowStatistic());
        CssLayout buttonsLayout = new CssLayout();
        buttonsLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        buttonsLayout.addComponents(addBtn, editBtn, removeBtn, showBtn);
        rootContentLayout.addComponent(buttonsLayout);
        rootContentLayout.setMargin(true);
        rootContentLayout.setSpacing(true);

        rootContentLayout.addComponent(mechanicGrid);

        updateDataMechanics();

        mechanicGrid.setSizeFull();
        mechanicGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        mechanicGrid.setColumnOrder("id", "firstName", "lastName", "fatherName", "hourlyPay");
        setContent(rootContentLayout);

    }

    private void removeMechanics() {
        Collection<Object> items = mechanicGrid.getSelectedRows();
        for (Object item : items) {
            Mechanic mechanic = (Mechanic) item;
            service.controller().deleteMechanic(mechanic);
        }
        if (items.isEmpty()) {
            showFailureNotification("Please, choose mechanic for edit");
        } else {
            showSuccessNotification("Mechanics was removed");
            updateDataMechanics();
        }
    }

    private void updateDataMechanics() {
        final BeanItemContainer<Mechanic> beanItemContainer =
                new BeanItemContainer<>(Mechanic.class, service.controller().getAllMechanics());
        mechanicGrid.setContainerDataSource(beanItemContainer);
    }

    private void openWindowNewMechanic() {
        AddingMechanicWindow modalWindow = new AddingMechanicWindow();
        UI.getCurrent().addWindow(modalWindow);
        modalWindow.addCloseListener(closeEvent -> updateDataMechanics());
    }

    private void openWindowEditMechanic() {
        if (mechanicGrid.getSelectedRows().size() > 1) {
            showFailureNotification("Please, choose once mechanic for edit");
        } else {

            Mechanic mechanicSelected = null;
            for (Object item : mechanicGrid.getSelectedRows()) {
                mechanicSelected = (Mechanic) item;
            }

            if (mechanicSelected == null) {
                showFailureNotification("Please, choose mechanic for edit");
            } else {
                EditingMechanicWindow modalWindow = new EditingMechanicWindow(mechanicSelected);
                UI.getCurrent().addWindow(modalWindow);
                modalWindow.addCloseListener(closeEvent -> updateDataMechanics());
            }
        }
    }

    private void openWindowStatistic() {
        List<Mechanic> mechanicsSelected = new ArrayList<>();
        for (Object item : mechanicGrid.getSelectedRows()) {
            mechanicsSelected.add((Mechanic) item);
        }

        if (mechanicsSelected.isEmpty()) {
            showFailureNotification("Please, choose mechanics for show statistic");
        } else {
            ShowingStatisticWindow modalWindow = new ShowingStatisticWindow(mechanicsSelected);
            UI.getCurrent().addWindow(modalWindow);
        }
    }

    private void showFailureNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
        notification.show(getUI().getPage());
    }

    private void showSuccessNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
        notification.show(getUI().getPage());
    }

}
