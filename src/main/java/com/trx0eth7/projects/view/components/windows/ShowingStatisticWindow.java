package com.trx0eth7.projects.view.components.windows;

import com.trx0eth7.projects.model.entity.Mechanic;
import com.trx0eth7.projects.model.entity.Order;
import com.trx0eth7.projects.view.services.WebService;
import com.vaadin.data.Item;
import com.vaadin.ui.*;

import java.util.List;

public class ShowingStatisticWindow extends Window {

    private Table statisticTable = new Table("Statistic by mechanic -> orders");
    private VerticalLayout content = new VerticalLayout();



    public ShowingStatisticWindow(List<Mechanic> mechanicsSelected) {
        WebService service = WebService.getInstance();
        center();
        setSizeFull();
        setResizable(false);
        setClosable(true);
        setModal(true);

        content.setMargin(true);
        content.setSpacing(true);
        content.setSizeFull();
        content.addComponents(statisticTable);

        statisticTable.setSizeFull();
        for (Mechanic mechanic : mechanicsSelected) {
            statisticTable.addContainerProperty(mechanic.getFirstName() + " " + mechanic.getLastName(), String.class, null);
            List<Order> orders = service.controller().getOrderByMechanic(mechanic);
            for (Order order : orders) {
                Object item = statisticTable.addItem();
                Item row = statisticTable.getItem(item);
                row.getItemProperty(mechanic.getFirstName() + " " + mechanic.getLastName())
                        .setValue("#" + order.getId() + " " + order.getDescription());
            }
        }

        setContent(content);
    }
}
