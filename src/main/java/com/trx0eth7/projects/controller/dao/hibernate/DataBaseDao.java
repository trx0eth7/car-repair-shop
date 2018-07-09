package com.trx0eth7.projects.controller.dao.hibernate;

import org.hibernate.Session;

public interface DataBaseDao {
    void closeSession();

    Session openSession();
}
