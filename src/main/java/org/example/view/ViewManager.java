package org.example.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewManager {

    private static Stage stage;
    private static Scene scene;

    public static void init(Stage primaryStage) {
        stage = primaryStage;
        scene = new Scene(new Parent() {}, 800, 600); // temp root
        stage.setScene(scene);
        stage.setTitle("Stock Management System");
        stage.show();
    }

    public static void setRoot(Parent root) {
        scene.setRoot(root);
    }

    public static void showLoginView() {
        setRoot(new LoginView());
    }

    public static void showSignupView(){
        setRoot(new SignupView());
    }

    public static void showDashboardView(){
        setRoot(new DashboardView());
    }
}
