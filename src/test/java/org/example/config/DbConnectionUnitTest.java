package org.example.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DbConnectionUnitTest {

    @BeforeAll
    public static void setup() {
        // Set the test environment property
        System.setProperty("test.env", "true");
    }

    /**
     * to pass this test env connection string
     * with valid user credential must be inserted
     */
    @Test
    public void testSessionFactoryInitialization() {
        assertNotNull(DbConnection.getSessionFactory(), "SessionFactory should not be null");

        String expectedTestDatabaseURL = Dotenv.load().get("TEST_DATABASE_URL");
        String actualDatabaseURL = DbConnection.getDatabaseURL();

        assertEquals(expectedTestDatabaseURL, actualDatabaseURL, "SessionFactory should be connected to the test database");
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

