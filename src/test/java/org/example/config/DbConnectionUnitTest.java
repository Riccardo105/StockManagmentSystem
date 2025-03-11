package org.example.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DbConnectionUnitTest {
    /**
     * to pass this test env connection string
     * with valid user credential must be inserted
     */
    @Test
    public void testSessionFactoryInitialization() {
        assertNotNull(DbConnection.getSessionFactory(), "SessionFactory should not be null");
    }
}

