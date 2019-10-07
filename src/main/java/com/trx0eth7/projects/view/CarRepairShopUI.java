package com.trx0eth7.projects.view;

import com.trx0eth7.projects.view.components.tabs.CustomerTabContent;
import com.trx0eth7.projects.view.components.tabs.MechanicTabContent;
import com.trx0eth7.projects.view.components.tabs.OrderTabContent;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "")
@Theme(ValoTheme.THEME_NAME)
public class CarRepairShopUI extends UI {

    @Autowired
    private CustomerTabContent customerTab;
    @Autowired
    private MechanicTabContent mechanicTab;
    @Autowired
    private OrderTabContent orderTab;

    private TabSheet navigationTab = new TabSheet();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        addNavigationTabs();
        addRootContent();
    }

    private void addRootContent() {
        Panel border = new Panel(navigationTab);
        border.setSizeFull();

        VerticalLayout rootContent = new VerticalLayout();
        rootContent.addComponent(border);
        setContent(rootContent);
    }

    private void addNavigationTabs() {
        navigationTab.setSizeFull();
        navigationTab.addTab(customerTab, "Customers");
        navigationTab.addTab(mechanicTab, "Mechanics");
        navigationTab.addTab(orderTab, "Orders");
    }
}
