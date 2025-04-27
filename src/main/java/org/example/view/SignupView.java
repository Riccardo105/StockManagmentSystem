package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class SignupView extends VBox {

    public SignupView() {
        this.setSpacing(20);
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.CENTER);

        Label title = new Label("Sign up");
        title.setFont(new Font("Arial", 24));

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        firstNameField.setMaxWidth(200);

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameField.setMaxWidth(200);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        TextField confirmPasswordField = new TextField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(200);

        Button SignupButton = new Button("Singup");
        SignupButton.setOnAction(e -> {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            ViewManager.showLoginView();
        });

        Hyperlink loginLink = new Hyperlink("Already have an account? login in");
        loginLink.setOnAction(e -> ViewManager.showLoginView());

        this.getChildren().addAll(title, firstNameField, lastNameField, emailField, passwordField, confirmPasswordField, loginLink);
    }
}
