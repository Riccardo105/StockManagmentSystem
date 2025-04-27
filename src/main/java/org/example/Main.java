package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.view.ViewManager;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        ViewManager.init(primaryStage);
        ViewManager.showDashboardView();
    }
    public static void main(String[] args) {
    launch(args);}
}
