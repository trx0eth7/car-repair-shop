package com.trx0eth7.projects.view.components.tabs;

import com.vaadin.annotations.Title;
import com.vaadin.shared.Position;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

public class BaseTabContent extends Panel {

    protected void showFailureNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
        notification.show(getUI().getPage());
    }

    protected void showSuccessNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
        notification.show(getUI().getPage());
    }

    protected void showWarningNotification(String caption) {
        Notification notification = new Notification(caption);
        notification.setDelayMsec(1000);
        notification.setPosition(Position.TOP_CENTER);
        notification.setStyleName(ValoTheme.NOTIFICATION_WARNING);
        notification.show(getUI().getPage());
    }

    @Title("Warning")
    class ConfirmWindow extends Window {
        private VerticalLayout rootContent = new VerticalLayout();
        private Button confirmBtn = new Button("Yes");
        private Button cancelBtn = new Button("No");
        private Label message = new Label("Are you sure?");
        private boolean confirm = false;

        ConfirmWindow() {
            requiredConfiguration();
            addRootContent();
        }

        private void addRootContent() {
            addButtonListeners();
            HorizontalLayout horizontalContent = new HorizontalLayout();
            horizontalContent.setSpacing(true);
            horizontalContent.addComponents(confirmBtn, cancelBtn);
            horizontalContent.setSizeFull();
            rootContent.addComponents(message, horizontalContent);
            rootContent.setComponentAlignment(horizontalContent, Alignment.BOTTOM_CENTER);
            rootContent.setComponentAlignment(message, Alignment.TOP_CENTER);
            rootContent.setSpacing(true);
            setContent(rootContent);
        }

        private void addButtonListeners() {
            confirmBtn.addClickListener((Button.ClickListener) closeEvent -> {
                confirm = true;
                close();
            });
            cancelBtn.addClickListener((Button.ClickListener) closeEvent -> close());
        }

        private void requiredConfiguration() {
            center();
            setResizable(false);
            setModal(true);
            setClosable(false);
        }

        boolean isConfirm() {
            return confirm;
        }
    }
}
