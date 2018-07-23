package com.trx0eth7.projects.view;

import com.trx0eth7.projects.view.services.WebService;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI(path = "")
@Theme(ValoTheme.THEME_NAME)
public class CarRepairShopUI extends UI {

    @Autowired
    private WebService service;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new Label("Test"));
    }
}
