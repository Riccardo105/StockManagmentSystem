package org.example.config;
import org.example.model.DTO.*;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DbConnection {
    private static  SessionFactory sessionFactory;

    static {
        try {

            Configuration configuration = new Configuration();
            // env variables setup through RUN->EDIT CONFIG
            String databaseURL = System.getenv("DATABASE_URL");
            if (databaseURL == null) {
                throw new IllegalStateException("DATABASE_URL is not set");
            }

            // Add entity mappings
            configuration.addAnnotatedClass(ProductDTO.class);
            configuration.addAnnotatedClass(BookDTO.class);
            configuration.addAnnotatedClass(MusicDTO.class);
            configuration.addAnnotatedClass(EBookDTO.class);
            configuration.addAnnotatedClass(PaperBookDTO.class);
            configuration.addAnnotatedClass(AudioBookDTO.class);
            configuration.addAnnotatedClass(CdDTO.class);
            configuration.addAnnotatedClass(DigitalDTO.class);
            configuration.addAnnotatedClass(VinylDTO.class);

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
            sessionFactory = null; // Set to null after closing
        }
    }

    // this method used only for: testing and initialising SessionFactory at program startup
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
