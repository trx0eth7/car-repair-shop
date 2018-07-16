package com.trx0eth7.projects.view.services;

import com.trx0eth7.projects.controller.EntityController;
import com.trx0eth7.projects.controller.dao.impl.CustomerDao;
import com.trx0eth7.projects.controller.dao.impl.MechanicDao;
import com.trx0eth7.projects.controller.dao.impl.OrderDao;
import org.hibernate.Session;

public class WebService {

    private static WebService instance;
    private EntityController controller;

    private WebService() {
    }

    private WebService(EntityController controller) {
        this.controller = controller;
        initDaoComponentsOfController(controller);
    }

    public static WebService getInstance() {
        if (instance == null) {
            instance = new WebService(new EntityController());
        }
        return instance;
    }

    private void initDaoComponentsOfController(EntityController controller) {
        Session session = controller.getSession();

        CustomerDao customerDao = new CustomerDao(session);
        MechanicDao mechanicDao = new MechanicDao(session);
        OrderDao orderDao = new OrderDao(session);

        controller.setCustomerDao(customerDao);
        controller.setMechanicDao(mechanicDao);
        controller.setOrderDao(orderDao);
    }

    public EntityController controller() {
        return controller;
    }


}
