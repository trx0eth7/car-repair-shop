package com.trx0eth7.projects.controller.dao.impl;

import com.trx0eth7.projects.controller.dao.AbstractDao;
import com.trx0eth7.projects.model.entity.Mechanic;
import org.hibernate.Session;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public class MechanicDao extends AbstractDao<Mechanic> {

    public MechanicDao(Session session) {
        super(session);
    }

    @Nullable
    public Mechanic findById(Long id) {
        Mechanic mechanic = null;
        try {
            mechanic = (Mechanic) session.get(Mechanic.class, id);
        } catch (Exception ignored) {
        }
        return mechanic;
    }

    public List<Mechanic> findByName(String name) {
        List<Mechanic> mechanics = Collections.emptyList();
        try {
            session.getTransaction().begin();
            mechanics = session.createSQLQuery("SELECT * FROM mechanics WHERE mechanics.firstName = '" + name + "' ORDER BY firstName")
                    .addEntity(Mechanic.class)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return mechanics;
    }

    public List<Mechanic> findAll() {
        List<Mechanic> mechanics = Collections.emptyList();
        try {
            session.getTransaction().begin();
            mechanics = session.createSQLQuery("SELECT * FROM mechanics ORDER BY id")
                    .addEntity(Mechanic.class)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
        return mechanics;

    }

}
