package org.example.config;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.controller.DashBoardController;
import org.example.model.Service.ProductSearchService;
import org.hibernate.SessionFactory;

public class GuiceConfig extends AbstractModule {

    @Provides
    public SessionFactory provideSessionFactory() {
        return DbConnection.getSessionFactory();

    }
    @Override
    protected void configure() {
        bind(DashBoardController.class).in(Singleton.class);
        bind(ProductSearchService.class).in(Singleton.class);
    }

}
