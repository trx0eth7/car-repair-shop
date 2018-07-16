package com.trx0eth7.projects.view.components.windows;

import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class ModalPopupWindow extends Window {

    protected VerticalLayout content = new VerticalLayout();
    protected FormLayout form = new FormLayout();

    public ModalPopupWindow() {
        super();
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

    void showSuccessNotification(String caption){
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
        notification.show(getUI().getPage());
    }
}
