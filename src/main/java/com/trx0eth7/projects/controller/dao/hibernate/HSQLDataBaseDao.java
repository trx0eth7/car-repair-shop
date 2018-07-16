package com.trx0eth7.projects.controller.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import static org.hibernate.boot.registry.StandardServiceRegistryBuilder.DEFAULT_CFG_RESOURCE_NAME;

public final class HSQLDataBaseDao implements DataBaseDao {
    private static HSQLDataBaseDao instance;
    private static final String PATH_CFG = "hibernate/" + DEFAULT_CFG_RESOURCE_NAME;

    private HSQLDataBaseDao() {
    }

    public static HSQLDataBaseDao getInstance() {
        if (instance == null) {
            instance = new HSQLDataBaseDao();
        }
        return instance;
    }

    public SessionFactory buildSessionFactoryByDefaultConfiguration() {
        Configuration cfg = new Configuration()
                .configure(PATH_CFG);
        return buildSessionFactory(cfg);
    }

    public SessionFactory buildSessionFactory(Configuration cfg) {
        try {
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(cfg.getProperties())
                    .build();
            return cfg.buildSessionFactory(serviceRegistry);
        } catch (HibernateException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
}
