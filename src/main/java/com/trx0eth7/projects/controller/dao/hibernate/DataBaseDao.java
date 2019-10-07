package com.trx0eth7.projects.controller.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public interface DataBaseDao {
    SessionFactory buildSessionFactory(Configuration cfg);
}
