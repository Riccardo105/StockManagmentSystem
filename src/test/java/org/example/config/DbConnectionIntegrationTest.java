package org.example.config;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DbConnectionIntegrationTest {

    // running this test assumes DbConnection unit test has passed
    @Test
    public void testSessionCreation() {
        Session session = DbConnection.getSession();
        assertNotNull(session);

    }
}
