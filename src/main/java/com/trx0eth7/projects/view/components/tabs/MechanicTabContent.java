package com.trx0eth7.projects.view.components.tabs;

import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import com.trx0eth7.projects.view.WebService;
import com.trx0eth7.projects.view.components.windows.MechanicAddingWindow;
import com.trx0eth7.projects.view.components.windows.MechanicEditingWindow;
import com.trx0eth7.projects.view.components.windows.StatisticsWindow;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringComponent
@Theme(ValoTheme.PANEL_BORDERLESS)
public class MechanicTabContent extends BaseTabContent implements SelectionEvent.SelectionListener {

    private final WebService service;
    private Grid mechanicsData = new Grid("Mechanics Data");
    private Button addBtn = new Button("Add");
    private Button editBtn = new Button("Edit");
    private Button deleteBtn = new Button("Delete");
    private Button showBtn = new Button("Show Statistics");

    private List<Mechanic> selectedMechanics = new ArrayList<>();

    @Autowired
    public MechanicTabContent(WebService service) {
        this.service = service;
        configureMechanicsGrid();
        updateDataMechanics();
        addButtonListeners();
        addRootContent();
    }

    private void configureMechanicsGrid() {
        mechanicsData.setSizeFull();
        mechanicsData.setSelectionMode(Grid.SelectionMode.MULTI);
        mechanicsData.addSelectionListener(this);
        mechanicsData.setColumns("lastName", "firstName", "fatherName", "payPerHour");
    }

    private void addButtonListeners() {
        addBtn.addClickListener((Button.ClickListener) clickEvent -> addMechanic());
        deleteBtn.addClickListener((Button.ClickListener) clickEvent -> deleteMechanics());
        editBtn.addClickListener((Button.ClickListener) clickEvent -> editMechanic());
        showBtn.addClickListener((Button.ClickListener) clickEvent -> showStatistics());
    }

    private void addRootContent() {
        VerticalLayout rootContent = new VerticalLayout();

        CssLayout buttonsContent = new CssLayout();
        buttonsContent.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        buttonsContent.addComponents(addBtn, editBtn, deleteBtn, showBtn);

        rootContent.addComponent(buttonsContent);
        rootContent.setMargin(true);
        rootContent.setSpacing(true);
        rootContent.addComponent(mechanicsData);
        setContent(rootContent);
    }

    private void deleteMechanics() {
        if (!selectedMechanics.isEmpty()) {
            ConfirmWindow confirmWindow = new ConfirmWindow();
            UI.getCurrent().addWindow(confirmWindow);
            confirmWindow.addCloseListener(closeEvent -> {
                if (confirmWindow.isConfirm()) {
                    for (Mechanic mechanic : selectedMechanics) {
                        List<Order> orders = service.controller().getOrdersByMechanic(mechanic);
                        if (!orders.isEmpty()) {
                            showWarningNotification(String.format("%s %s has an unexecuted Order, you cannot delete him",
                                    mechanic.getFirstName(), mechanic.getLastName()));
                            return;
                        }
                    }
                    service.controller().deleteMechanics(selectedMechanics);
                    updateDataMechanics();
                    showSuccessNotification("Mechanics were deleted");
                }
            });
        } else {
            showWarningNotification("Please, select at least one Mechanic");
        }
    }

    private void updateDataMechanics() {
        BeanItemContainer<Mechanic> beanItemContainer =
                new BeanItemContainer<>(Mechanic.class, service.controller().getAllMechanics());
        mechanicsData.setContainerDataSource(beanItemContainer);
    }

    private void addMechanic() {
        MechanicAddingWindow modalWindow = new MechanicAddingWindow(service);
        UI.getCurrent().addWindow(modalWindow);
        modalWindow.addCloseListener(closeEvent -> updateDataMechanics());
    }

    private void editMechanic() {
        if (!selectedMechanics.isEmpty()) {
            if (selectedMechanics.size() == 1) {
                MechanicEditingWindow modalWindow = new MechanicEditingWindow(selectedMechanics.get(0), service);
                UI.getCurrent().addWindow(modalWindow);
                modalWindow.addCloseListener(closeEvent -> {
                    updateDataMechanics();
                    selectedMechanics.clear();
                });
                mechanicsData.deselectAll();
            } else {
                showWarningNotification("Please, select only one Mechanic");
            }
        } else {
            showWarningNotification("Please, select a Mechanic");
        }
    }

    private void showStatistics() {
        if (!selectedMechanics.isEmpty()) {
            StatisticsWindow modalWindow = new StatisticsWindow(service, selectedMechanics);
            UI.getCurrent().addWindow(modalWindow);
        } else {
            showWarningNotification("Please, select at least one Mechanic");
        }
    }

    @Override
    public void select(SelectionEvent event) {
        selectedMechanics.clear();
        for (Object item : mechanicsData.getSelectionModel().getSelectedRows()) {
            selectedMechanics.add((Mechanic) item);
        }
    }
}
