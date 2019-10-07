package com.trx0eth7.projects.controller.dao.hibernate;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HSQLDataBaseDaoIntegrationTest {

    HSQLDataBaseDao db;

    @Before
    public void setUp() {
        db = HSQLDataBaseDao.getInstance();
    }

    @Test
    public void shouldCreateSession() {
        Session session = db.buildSessionFactoryByDefaultConfiguration().openSession();
        Assert.assertNotNull(session);
    }
}
