package com.trx0eth7.projects.controller.dao.impl;

import com.trx0eth7.projects.controller.dao.AbstractDao;
import com.trx0eth7.projects.model.entity.Mechanic;
import org.hibernate.Session;

public class MechanicDao extends AbstractDao<Mechanic> {

    public MechanicDao(Session session) {
        super(session);
    }

    public Mechanic get(long id) {
        return null;
    }
}
