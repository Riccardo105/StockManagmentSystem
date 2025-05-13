package org.example.config;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.example.controller.DashBoardController;
import org.example.controller.LoginController;
import org.example.controller.SignupController;
import org.example.model.Service.ProductSearchService;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GuiceConfig extends AbstractModule {

    @Provides
    public SessionFactory provideSessionFactory() {
        return DbConnection.getSessionFactory();

    }

    @Provides
    @Singleton
    public BCryptPasswordEncoder providePasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    @Override
    protected void configure() {
        bind(DashBoardController.class).in(Singleton.class);
        bind(SignupController.class).in(Singleton.class);
        bind(LoginController.class).in(Singleton.class);
        bind(ProductSearchService.class).in(Singleton.class);
    }

}
