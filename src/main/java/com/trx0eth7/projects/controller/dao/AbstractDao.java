package com.trx0eth7.projects.controller.dao;

import com.trx0eth7.projects.model.entity.IEntity;
import org.hibernate.Session;

public abstract class AbstractDao<T extends IEntity> {

    protected AbstractDao(Session session) {
        this.session = session;
    }

    private Session session;

    public boolean insert(T entity){
        try {
            session.getTransaction().begin();
            session.persist(entity);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            session.getTransaction().rollback();
            return false;
        }
    }

    public abstract T get(long id);

    public boolean update(T entity) {
        try {
            session.getTransaction().begin();
            session.merge(entity);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
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
        }catch (Exception e){
            session.getTransaction().rollback();
            return false;
        }
    }

    Session getSession() {
        return session;
    }
}
