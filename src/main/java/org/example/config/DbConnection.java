package org.example.config;
import io.github.cdimascio.dotenv.Dotenv;
import org.example.model.DTO.AccessControl.*;
import org.example.model.DTO.products.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton connection to the database, sets static sessionFactory at startup
 * used throughout the app's lifecycle to operate on the database
 */
public class DbConnection {
    private static  SessionFactory sessionFactory;
    private static final Dotenv dotenv = Dotenv.load();
    private static final String databaseURL;

    static {
        try {

            Configuration configuration = new Configuration();
            boolean isTestEnvironment = System.getProperty("test.env") != null; // Check if running in test mode

            if (isTestEnvironment) {
                databaseURL = dotenv.get("TEST_DATABASE_URL"); // Use test database
            } else {
                databaseURL = dotenv.get("DATABASE_URL"); // Use production database
            }
            if (databaseURL == null) {
                throw new IllegalStateException("DATABASE_URL is not set");
            }

            // Add entities to mapping
            configuration.addAnnotatedClass(ProductDTO.class);
            configuration.addAnnotatedClass(BookDTO.class);
            configuration.addAnnotatedClass(MusicDTO.class);
            configuration.addAnnotatedClass(EBookDTO.class);
            configuration.addAnnotatedClass(PaperBookDTO.class);
            configuration.addAnnotatedClass(AudioBookDTO.class);
            configuration.addAnnotatedClass(CdDTO.class);
            configuration.addAnnotatedClass(DigitalDTO.class);
            configuration.addAnnotatedClass(VinylDTO.class);
            configuration.addAnnotatedClass(OperationDTO.class);
            configuration.addAnnotatedClass(ResourceDTO.class);
            configuration.addAnnotatedClass(UserDTO.class);
            configuration.addAnnotatedClass(PermissionsDTO.class);
            configuration.addAnnotatedClass(RoleDTO.class);
            configuration.addAnnotatedClass(RolePermissionsDTO.class);
            configuration.addAnnotatedClass(RolePermissionsId.class);
            configuration.addAnnotatedClass(UserRoleDTO.class);
            configuration.addAnnotatedClass(UserRoleId.class);

            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", databaseURL);
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperty("hibernate.connection.pool_size", "5");

            // SQL logging
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");

            configuration.setProperty("hibernate.hbm2ddl.auto", "validate");

            sessionFactory = configuration.buildSessionFactory();

            } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);

            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void SessionFactoryShutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }


    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static String getDatabaseURL() {
        return databaseURL; // Expose the database URL
    }

}
