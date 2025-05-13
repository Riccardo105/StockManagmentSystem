package org.example.view.partials;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.LoginController;
import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountActivation extends VBox {

    private final Map<UserDTO, ComboBox<String>> roleSelections = new HashMap<>();
    private final List<RoleDTO> availableRoles;
    private final LoginController loginController;

    // success or error message
    private final Label message;

    public AccountActivation(List<UserDTO> users, List<RoleDTO> availableRoles, LoginController loginController) {
        this.availableRoles = availableRoles;
        this.loginController = loginController;

        setSpacing(10);
        setPadding(new Insets(20));

        for (UserDTO user : users) {
            HBox row = new HBox(10);
            row.setPadding(new Insets(5));

            Label nameLabel = new Label(user.getFirstName());
            Label surnameLabel = new Label(user.getLastName());

            ComboBox<String> roleBox = new ComboBox<>();
            for (RoleDTO role : availableRoles) {
                roleBox.getItems().add(role.getName());
            }

            roleSelections.put(user, roleBox);

            row.getChildren().addAll(nameLabel, surnameLabel, roleBox);
            getChildren().add(row);
        }

        message = new Label("");
        HBox messageWrapper = new HBox(message);
        messageWrapper.setAlignment(Pos.CENTER);
        messageWrapper.setPadding(new Insets(10, 0, 0, 0));

        Button activateButton = getActivateButton();
        HBox buttonWrapper = new HBox(activateButton);
        buttonWrapper.setAlignment(Pos.CENTER);
        buttonWrapper.setPadding(new Insets(10, 0, 0, 0));


        getChildren().addAll( messageWrapper, buttonWrapper);
    }

    private Button getActivateButton() {
        Button activateButton = new Button("Activate");

        message.setText("");

        activateButton.setOnAction(e -> {
            boolean allValid = true;
            Map<UserDTO, RoleDTO> userRoleMap = new HashMap<>();

            for (Map.Entry<UserDTO, ComboBox<String>> entry : roleSelections.entrySet()) {
                UserDTO user = entry.getKey();
                ComboBox<String> comboBox = entry.getValue();
                String selectedRoleName = comboBox.getValue();

                if (selectedRoleName == null || selectedRoleName.isEmpty()) {
                    comboBox.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                    comboBox.setTooltip(new Tooltip("Role must be selected"));
                    allValid = false;
                } else {
                    comboBox.setStyle("");
                    comboBox.setTooltip(null);

                    RoleDTO matchedRole = null;
                    for (RoleDTO role : availableRoles) {
                        if (role.getName().equals(selectedRoleName)) {
                            matchedRole = role;
                            break;
                        }
                    }

                    if (matchedRole != null) {
                        userRoleMap.put(user, matchedRole);
                    } else {
                        System.err.println("Role not found for name: " + selectedRoleName);
                        allValid = false;
                    }
                }
            }

            if (allValid) {
                try {
                    loginController.handleAccountActivation(userRoleMap);
                    message.setText("Accounts activated successfully");
                    message.setStyle("font-color: green;");

                    new Thread(() -> {
                        try {
                            Thread.sleep(300); // delay before closing
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                        Platform.runLater(() -> {
                            Stage stage = (Stage) getScene().getWindow();
                            stage.close();
                        });
                    }).start();

                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                    message.setText("error while activating account");
                    message.setStyle("font-color: red;");
                }
            }
        });

        return activateButton;
    }
}