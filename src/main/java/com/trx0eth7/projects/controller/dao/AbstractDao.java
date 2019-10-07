package com.trx0eth7.projects.controller.dao;

import com.trx0eth7.projects.model.entity.IEntity;
import org.hibernate.Session;

import java.util.List;

/*
 * Don't use this class, as it is used for Spring
 */
@Deprecated
public abstract class AbstractDao<T extends IEntity> {
    private Session session;

    protected AbstractDao(Session session) {
        this.session = session;
    }

    public abstract List<T> findByName(String name);

    public abstract List<T> findAll();

    public abstract T findById(Long id);

    public void insert(T entity) {
        try {
            session.getTransaction().begin();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public void update(T entity) {
        try {
            session.getTransaction().begin();
            session.merge(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public void delete(T entity) {
        try {
            session.getTransaction().begin();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    public Session getSession() {
        return session;
    }
}
