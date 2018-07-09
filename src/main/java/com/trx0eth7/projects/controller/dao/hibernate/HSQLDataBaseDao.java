package com.trx0eth7.projects.controller.dao.hibernate;

import com.trx0eth7.projects.model.entity.Customer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import static org.hibernate.boot.registry.StandardServiceRegistryBuilder.DEFAULT_CFG_RESOURCE_NAME;

public final class HSQLDataBaseDao implements DataBaseDao {
    private static HSQLDataBaseDao instance;
    private SessionFactory sessionFactory;
    private static final String PATH_CFG = "hibernate/" + DEFAULT_CFG_RESOURCE_NAME;


    public static HSQLDataBaseDao getInstance() {
        if (instance == null) {
            instance = new HSQLDataBaseDao();
        }
        return instance;
    }

    private HSQLDataBaseDao() {
        initConnections();
    }

    private void initConnections() {
        Configuration cfg = new Configuration()
                .addAnnotatedClass(Customer.class)
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
