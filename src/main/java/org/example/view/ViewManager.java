package org.example.view;

import com.google.inject.Injector;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {

    private static Stage stage;
    private static Scene scene;
    private static Injector injector;

    public static void init(Stage primaryStage, Injector GuiceInjector) {
        injector = GuiceInjector;
        stage = primaryStage;
        stage.setResizable(false);
        scene = new Scene(new Parent() {}, 800, 600); // temp root
        stage.setScene(scene);
        stage.setTitle("Stock Management System");
        stage.show();
    }

    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }

    public static void showLoginView() {
        setRoot(injector.getInstance(LoginView.class));
    }

    public static void showSignupView(){
        setRoot(injector.getInstance(SignupView.class));
    }

    public static void showDashboardView(){
        setRoot(injector.getInstance(DashboardView.class));
    }
}
