package org.example.config;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.hibernate.SessionFactory;

public class GuiceConfig extends AbstractModule {

    @Provides
    public SessionFactory provideSessionFactory() {
        return DbConnection.getSessionFactory();
    }
}
