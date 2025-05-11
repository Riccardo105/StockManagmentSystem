package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.example.controller.SignupController;
import org.example.view.partials.ProductFormBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignupView extends VBox {

    private Map<String, String> errorMap;
    private final SignupController signupController = new SignupController();

    // form fields added to map for ease in mapping errors to ui widgets
    public Map<String, Control> fieldMap = new HashMap<>();

    public SignupView() {
        this.setSpacing(20);
        this.setPadding(new Insets(30));
        this.setAlignment(Pos.CENTER);

        Label title = new Label("Sign up");
        title.setFont(new Font("Arial", 24));

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("First Name");
        firstNameField.setMaxWidth(200);
        fieldMap.put("firstName", firstNameField);

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Last Name");
        lastNameField.setMaxWidth(200);
        fieldMap.put("lastName", lastNameField);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);
        fieldMap.put("email", emailField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);
        fieldMap.put("password", passwordField);

        TextField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setMaxWidth(200);
        fieldMap.put("confirmPassword", confirmPasswordField);

        Button SignupButton = new Button("Singup");
        SignupButton.setOnAction(e -> {
            if (errorMap != null && !errorMap.isEmpty()) {
                errorMap.clear();
            }

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            HashMap<String, String> formData = new HashMap<>();
            formData.put("firstName", firstName);
            formData.put("lastName", lastName);
            formData.put("email", email);
            formData.put("password", password);
            formData.put("confirmPassword", confirmPassword);

            signupHandler(formData);

            if (!errorMap.isEmpty()) {
                mapErrorsToFormFields(errorMap);
            }

        });

        Hyperlink loginLink = new Hyperlink("Already have an account? login in");
        loginLink.setOnAction(e -> ViewManager.showLoginView());

        this.getChildren().addAll(title, firstNameField, lastNameField, emailField, passwordField, confirmPasswordField, SignupButton, loginLink);
    }

    private void signupHandler( HashMap<String, String> formData) {
        errorMap = new HashMap<>();
        for (Map.Entry<String, String> entry : formData.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            if (entry.getValue().isEmpty()) {
                errorMap.put(entry.getKey(), "field must not be null");
            }
        }
         if (!formData.get("email").contains("@")) {
            errorMap.put("email", "Invalid email address format");
         }

        if (!Objects.equals(formData.get("confirmPassword"), formData.get("password"))) {
            errorMap.put("confirmPassword", "Passwords do not match");
        }

        // only if the previous preliminary checks have not returned errors, send form to back end
        if (errorMap.isEmpty()) {
            errorMap = signupController.signupHandler(formData);
        }
    }

    private void mapErrorsToFormFields(Map<String, String> errorMap) {
        fieldMap.values().forEach(field -> {
            field.setStyle(null); // Reset any previous styles
            Tooltip.uninstall(field, null); // Remove any previous tooltip
        });

        if (!errorMap.isEmpty()) {
            errorMap.forEach((fieldName, errorMsg) -> {
                Control field = fieldMap.get(fieldName);
                field.setStyle("-fx-border-color: red;");
                Tooltip tooltip = new Tooltip(errorMsg);
                Tooltip.install(field, tooltip);
            });

        }
    }
}
