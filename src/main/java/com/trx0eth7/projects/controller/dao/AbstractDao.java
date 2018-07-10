package com.trx0eth7.projects.controller.dao;

import com.trx0eth7.projects.model.entity.IEntity;
import org.hibernate.Session;

import java.util.List;

public abstract class AbstractDao<T extends IEntity> {

    protected AbstractDao(Session session) {
        this.session = session;
    }

    protected Session session;

    public abstract List<T> findByName(String name);

    public abstract List<T> findAll();

    public abstract T findById(Long id);

    public boolean insert(T entity) {
        try {
            session.getTransaction().begin();
            session.save(entity);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean update(T entity) {
        try {
            session.getTransaction().begin();
            session.merge(entity);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public boolean delete(T entity) {
        try {
            session.getTransaction().begin();
            session.delete(entity);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
    }

    public Session getSession() {
        return session;
    }
}
