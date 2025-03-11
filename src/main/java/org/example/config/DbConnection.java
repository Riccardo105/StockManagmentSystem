package org.example.config;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DbConnection {
    private static final SessionFactory sessionFactory;

    static {
        try {

            Configuration configuration = new Configuration();
            // env variables setup through RUN->EDIT CONFIG
            String databaseURL = System.getenv("DB_URL");

            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");;
            configuration.setProperty("hibernate.connection.url", databaseURL);
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);

            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    // this method used only for: testing and initialising SessionFactory at program startup
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
