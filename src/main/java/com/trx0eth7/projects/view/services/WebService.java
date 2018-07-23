package com.trx0eth7.projects.view.services;

import com.trx0eth7.projects.controller.EntityController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebService {

    @Autowired
    private EntityController controller;

    public EntityController controller() {
        return controller;
    }

}
