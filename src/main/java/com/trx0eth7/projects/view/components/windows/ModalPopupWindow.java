package com.trx0eth7.projects.view.components.windows;

import com.trx0eth7.projects.view.WebService;
import com.vaadin.shared.Position;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
public class ModalPopupWindow extends Window {

    private VerticalLayout content = new VerticalLayout();
    FormLayout form = new FormLayout();
    final protected WebService service;

    ModalPopupWindow(WebService service) {
        this.service = service;
        requiredConfiguration();
    }

    private void requiredConfiguration() {
        center();
        setResizable(false);
        setClosable(false);
        setModal(true);
        form.setMargin(true);
        form.setSpacing(true);
        content.setWidthUndefined();
        content.addComponent(form);
        content.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
        setContent(content);
    }

    public void showSuccessNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
        notification.show(getUI().getPage());
    }

    public void showFailureNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
        notification.show(getUI().getPage());
    }

    public void showWarningNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_WARNING);
        notification.show(getUI().getPage());
    }
}
