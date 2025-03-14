package org.example.config;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DbConnectionUnitTest {
    /**
     * to pass this test env connection string
     * with valid user credential must be inserted
     */
    @Test
    public void testSessionFactoryInitialization() {
        assertNotNull(DbConnection.getSessionFactory(), "SessionFactory should not be null");
    }

    @Test
    public void testSessionFactoryShutdown() {
        DbConnection.SessionFactoryShutdown();

        assertNull(DbConnection.getSessionFactory(), "SessionFactory should be null");
        Exception exception = assertThrows(NullPointerException.class, () -> {
            Session session = DbConnection.getSession();

        });
    }
}

