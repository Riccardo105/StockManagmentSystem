package org.example.view;

import com.google.inject.Inject;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import org.example.controller.LoginController;
import org.example.model.DTO.AccessControl.UserDTO;

import java.util.List;

/**
 * login view error handling does not follow the errorMap system like the rest of the app
 * due to simplicity of error typology
 */
public class LoginView extends VBox {

    private final LoginController loginController;

    @Inject
    public LoginView( LoginController loginController) {
        this.loginController = loginController;
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

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: red;");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(250);
        messageLabel.setAlignment(Pos.CENTER);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> {
            messageLabel.setText("");
            String email = emailField.getText();
            String password = passwordField.getText();

            try {
                loginController.handleLogin(email, password);
                messageLabel.setText("Logging in...");
            }catch (IllegalArgumentException exc){
                messageLabel.setText(exc.getMessage());
            }
            catch (RuntimeException ex) {
                ex.printStackTrace();
                messageLabel.setText("unexpected error occurred");

            }

        });

        Hyperlink signupLink = new Hyperlink("New here? Sign up");
        signupLink.setOnAction(e -> ViewManager.showSignupView());

        this.getChildren().addAll(title, emailField, passwordField, messageLabel, loginButton, signupLink);
    }

}
