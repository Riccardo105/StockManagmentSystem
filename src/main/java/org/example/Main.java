package org.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import org.example.config.GuiceConfig;
import org.example.view.ViewManager;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        Injector injector = Guice.createInjector(new GuiceConfig());
        ViewManager.init(primaryStage, injector);
        ViewManager.showDashboardView();
    }
    public static void main(String[] args) {
        launch(args);}
}
