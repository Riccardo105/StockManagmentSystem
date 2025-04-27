package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


public class LoginView extends VBox {

    public LoginView() {
        this.setSpacing(20);
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.CENTER);

        Label title = new Label("Log in");
        title.setFont(new Font("Arial", 24));

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            String username = emailField.getText();
            String password = passwordField.getText();

            System.out.println("Logging in with: " + username + " / " + password);

        });

        Hyperlink signupLink = new Hyperlink("New here? Sign up");
        signupLink.setOnAction(e -> ViewManager.showSignupView());

        this.getChildren().addAll(title, emailField, passwordField, loginButton, signupLink);
    }
}
