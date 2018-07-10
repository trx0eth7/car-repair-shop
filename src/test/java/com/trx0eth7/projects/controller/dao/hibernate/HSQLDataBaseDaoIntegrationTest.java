package com.trx0eth7.projects.controller.dao.hibernate;

import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HSQLDataBaseDaoIntegrationTest {

    DataBaseDao db;

    @Before
    public void setUp() {
        db = new HSQLDataBaseDao();
    }

    @Test
    public void shouldCreateSession() {
        Session session = db.openSession();
        Assert.assertNotNull(session);
    }
}
