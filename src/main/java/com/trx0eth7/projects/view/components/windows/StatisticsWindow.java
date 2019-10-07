package com.trx0eth7.projects.view.components.windows;

import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import com.trx0eth7.projects.view.WebService;
import com.vaadin.annotations.Title;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;

import java.util.List;

@UIScope
@SpringComponent
@Title(StatisticsWindow.DEFAULT_TITLE)
public class StatisticsWindow extends Window {

    public static final String DEFAULT_TITLE = "Statistics";
    private final List<Mechanic> mechanicsSelected;
    private TabSheet navigationTabsSheet = new TabSheet();
    private final WebService service;
    private int countOrders;

    public StatisticsWindow(WebService service, List<Mechanic> mechanicsSelected) {
        this.service = service;
        this.mechanicsSelected = mechanicsSelected;
        requiredConfiguration();
        addNavigationTabs();
        addRootContent();
    }

    private void addNavigationTabs() {
        navigationTabsSheet.setSizeFull();
        for (Mechanic mechanic : mechanicsSelected) {
            VerticalLayout rootContentForTab = new VerticalLayout();
            rootContentForTab.setMargin(true);
            rootContentForTab.setSpacing(true);
            rootContentForTab.addComponent(createGridByMechanic(mechanic));
            Panel mechanicTab = new Panel(String.format("%s %s(%d)", mechanic.getFirstName(), mechanic.getLastName(), countOrders));
            mechanicTab.setContent(rootContentForTab);
            navigationTabsSheet.addTab(mechanicTab);
        }
    }

    private Grid createGridByMechanic(Mechanic mechanic) {
        BeanItemContainer<Order> beanItemContainer =
                new BeanItemContainer<>(Order.class, service.controller().getOrdersByMechanic(mechanic));
        countOrders = beanItemContainer.size();
        Grid statisticData = new Grid("Statistic Data");
        statisticData.setSizeFull();
        statisticData.setSelectionMode(Grid.SelectionMode.NONE);
        statisticData.setColumns("description", "customer", "startDate", "dueDate", "orderStatus", "cost");
        statisticData.setContainerDataSource(beanItemContainer);

        return statisticData;
    }

    private void addRootContent() {
        Panel border = new Panel(navigationTabsSheet);
        border.setSizeFull();

        VerticalLayout rootContent = new VerticalLayout();
        rootContent.setMargin(true);
        rootContent.setSpacing(true);
        rootContent.setSizeFull();

        rootContent.addComponent(border);
        setContent(rootContent);
    }

    private void requiredConfiguration() {
        center();
        setSizeFull();
        setResizable(false);
        setClosable(true);
        setModal(true);
    }
}
