package com.trx0eth7.projects.controller.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import static org.hibernate.boot.registry.StandardServiceRegistryBuilder.DEFAULT_CFG_RESOURCE_NAME;

public final class HSQLDataBaseDao implements DataBaseDao {
    private SessionFactory sessionFactory;
    private static final String PATH_CFG = "hibernate/" + DEFAULT_CFG_RESOURCE_NAME;

    public HSQLDataBaseDao() {
        initConnections();
    }

    private void initConnections() {
        Configuration cfg = new Configuration()
                .configure(PATH_CFG);
        sessionFactory = buildSessionFactory(cfg);
    }

    private SessionFactory buildSessionFactory(Configuration cfg) {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties())
                    .build();
            return cfg.buildSessionFactory(serviceRegistry);
        } catch (HibernateException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public void closeSession() {
        sessionFactory.close();
    }

}
