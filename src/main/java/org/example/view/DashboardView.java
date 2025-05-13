package org.example.view;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.ProductType;
import org.example.controller.DashBoardController;
import org.example.controller.LoginController;
import org.example.model.DTO.AccessControl.OperationDTO;
import org.example.model.DTO.AccessControl.PermissionDTO;
import org.example.model.DTO.AccessControl.ResourceDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.example.model.DTO.products.ProductDTO;
import org.example.model.Service.PermissionManager;
import org.example.model.Service.UserService;
import org.example.view.partials.*;

import java.util.*;


public class DashboardView extends VBox {

    private final DashBoardController dashBoardController;
    private final LoginController loginController;
    private final Map<String, String> errorMap = new HashMap<>();

    // widget instantiated here to be accessible by configurePermissions()
    private final ProductInfoPreview productsPreview;
    private final  HBox searchBar;
    private final ComboBox<ProductType> createNew;
    private final Button stockReport;
    private final Button saveChanges;


    @Inject
    public DashboardView(DashBoardController dashBoardController, LoginController loginController) {

        this.dashBoardController = dashBoardController;
        this.loginController = loginController;
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        //product table
        productsPreview = new ProductInfoPreview();

        // search bar
        // callback points to productPreview method
        searchBar = new SearchBar(dashBoardController, productsPreview::updateTableEntries);

        // create new button
        createNew = new ComboBox<>();
        createNew.getItems().addAll(ProductType.values());
        createNew.setPromptText("Create new");
        createNew.setOnAction(event -> {
            ProductType selectedProduct = createNew.getValue();
            if (selectedProduct != null) {
                showCreateForm(selectedProduct);
            }
        });

        // print stock report button
        stockReport = new Button("Stock Report");
        stockReport.setOnAction(event -> {
            dashBoardController.handleStockReport();
        });

        // undo changes button
        Button undoChanges = new Button("Undo Changes");
        undoChanges.setOnAction(event -> {
            productsPreview.undoChanges();
        });

        Button logout = new Button("Logout");
        logout.setOnAction(event -> {
            loginController.handleLogout();
        });

        HBox controlPanel = new HBox(logout, undoChanges, stockReport, createNew);
        HBox.setMargin(stockReport, new Insets(0, 10, 0, 10));
        HBox.setMargin(logout, new Insets(0, 375, 0, 10));
        controlPanel.setAlignment(Pos.TOP_RIGHT);
        controlPanel.setMaxWidth(Double.MAX_VALUE);

        VBox.setVgrow(controlPanel, Priority.ALWAYS);

        // save changes
        saveChanges = new Button("Save changes");
        saveChanges.setOnAction(event -> {
            productsPreview.saveChanges(dashBoardController);
        });

        getChildren().addAll(searchBar, controlPanel, productsPreview, saveChanges);

        // permission checked as soon as dashboard is initialised
        configurePermissions();

    }

    /**
     * this method manages the creation of ProductFormBuilder partial
     *
     * @param productType given by the "create new" drop-down menu
     */
    private void showCreateForm(ProductType productType) {

        Stage createProductStage = new Stage();
        createProductStage.setTitle("Create New " + productType.name());
        createProductStage.initModality(Modality.APPLICATION_MODAL);

        ScrollPane form = ProductFormBuilder.ProductFormManager(productType);

        // shows success or fail message upon product creation
        Label message = new Label();
        message.setWrapText(true);
        message.setVisible(false);
        HBox messageBox = new HBox(message);
        messageBox.setAlignment(Pos.CENTER);

        Button createButton = new Button("Create");
        createButton.setOnAction(event -> {
            // reset errors status
            clearFormFieldErrors();
            message.setVisible(false);

            // form data maps the  fields to their values taken from the current fieldMap
            Map<String, Object> formData = new HashMap<>();

            // these are used to check the correctness of the user input
            Set<String> intFields = Set.of("stock", "tracksNum", "numOfDiscs", "bitrateMbps", "numPages", "rpm", "size");
            Set<String> floatFields = Set.of("buyingPrice", "sellingPrice", "fileSize");

            ProductFormBuilder.fieldMap.forEach((fieldName, control) -> {

                if (control instanceof TextField) {
                    String input = ((TextField) control).getText();

                    if (input == null || input.isBlank()) {
                        formData.put(fieldName, null);
                        return;
                    }

                    if (fieldName.equals("playTime")) {
                        try {
                            int hours =  Integer.parseInt(input) / 60;
                            int minutes =  Integer.parseInt(input) % 60;

                            String timeString = String.format("%02d:%02d:00", hours, minutes);
                            formData.put(fieldName, timeString);
                            return;

                        } catch (NumberFormatException e ) {
                            formData.put(fieldName, null);
                            errorMap.put(fieldName, "format playtime in minutes e.g: 235");
                            return;
                        }
                    }

                    try {
                        if (intFields.contains(fieldName)) {
                            formData.put(fieldName, Integer.parseInt(input));
                        } else if (floatFields.contains(fieldName)) {
                            formData.put(fieldName, Float.parseFloat(input));
                        } else {
                            formData.put(fieldName, input);
                        }
                    } catch (NumberFormatException e) {
                        errorMap.put(fieldName, "this field must be a number");

                    }
                } else if (control instanceof DatePicker) {
                    formData.put(fieldName, ((DatePicker) control).getValue());
                }
            });

            // 1st check on input type (string or int)
            mapErrorsToFormFields(errorMap);
            errorMap.clear();

            // if initial check pass dashboardController is called and errors are stored
            try{
                errorMap.putAll(dashBoardController.handleCreateProduct(productType, formData));
            } catch (RuntimeException e) {
                message.setText("Database operation unsuccessful");
                message.setStyle("-fx-text-fill: red;");
                message.setVisible(true);
                return;
            }

            // 2nd check trough product's service layer
            mapErrorsToFormFields(errorMap);

            if (errorMap.isEmpty()) {
                message.setText("Product created successfully");
                message.setStyle("-fx-text-fill: green;");
                message.setVisible(true);
            }


        });

        HBox buttonContainer = new HBox(createButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        VBox formContainer = new VBox(20);
        formContainer.getChildren().addAll(form, messageBox, buttonContainer);
        formContainer.setPadding(new Insets(20));


        Scene createProductScene = new Scene(formContainer, 400, 400);

        createProductStage.setScene(createProductScene);
        createProductStage.showAndWait();
    }

    /**
     * this method is used to map the discovered errors back to the form's fields.
     * this is achieved by keeping track of the field name
     * @param errorMap, this map, maps the label of the field to its error message
     */
    private void mapErrorsToFormFields(Map<String, String> errorMap) {
        if (!errorMap.isEmpty()) {
            errorMap.forEach((fieldName, errorMsg) -> {
                Control field = ProductFormBuilder.fieldMap.get(fieldName);
                field.setStyle("-fx-border-color: red;");
                Tooltip tooltip = new Tooltip(errorMsg);
                Tooltip.install(field, tooltip);
            });

        }
    }

    /**
     * used to refresh all styling ad clear errorMap upon button click
     */
    private void clearFormFieldErrors() {
        errorMap.clear();
        ProductFormBuilder.fieldMap.forEach((fieldName, control) -> {
            if (control instanceof TextField textField) {
                textField.setStyle(""); // clear border
                textField.setTooltip(null); // clear tooltip
            } else if (control instanceof DatePicker datePicker) {
                datePicker.setStyle("");
                datePicker.setTooltip(null);
            }
        });
    }


    /**
     * @param product the product info window created in the preview table
     */
    public static void showProductDetailWindow(ProductDTO product) {
        Stage detailStage = new Stage();
        detailStage.setTitle("Product Details");

        // Set modality to make it a modal window (blocks interaction with parent window)
        detailStage.initModality(Modality.APPLICATION_MODAL);

        // Create the product view using our reusable partial
        VBox productView = ProductInfo.createProductView(product);
        productView.setPadding(new Insets(15));

        // Create scene and set it on the stage
        Scene scene = new Scene(productView, 400, 750); // Width: 400, Height: 600
        detailStage.setScene(scene);

        // Configure window behavior
        detailStage.setResizable(true);
        detailStage.setMinWidth(350);
        detailStage.setMinHeight(400);

        // Show the window
        detailStage.showAndWait();
    }

    /**
     * shows notification box for account that need activation to admin accounts
     * @param usersToActivate list of user whom account is not active
     */
    public void showActivateAccountWindow(Stage parentStage, List<UserDTO> usersToActivate) {
        Stage activateStage = new Stage();
        activateStage.setTitle("Account(s) need attention");

        activateStage.initModality(Modality.WINDOW_MODAL);
        activateStage.initOwner(parentStage);

        // Create the AccountActivation view
        VBox activationView = new AccountActivation( usersToActivate, loginController.getAvailableRoles(), loginController);
        activationView.setPadding(new Insets(15));

        // Create the scene with a landscape-oriented size
        Scene scene = new Scene(activationView);
        activateStage.setScene(scene);

        // Configure window behavior
        activateStage.setWidth(400);


        // Show the modal window and wait for it to close
        activateStage.showAndWait();
    }

    private void configurePermissions(){
        productsPreview.getProductsTable().setEditable(PermissionManager.canUpdateProducts(UserService.getCurrentUserPermissions()));
        searchBar.setDisable(!PermissionManager.canSearchProducts(UserService.getCurrentUserPermissions()));
        createNew.setDisable(!PermissionManager.canCreateProduct(UserService.getCurrentUserPermissions()));
        stockReport.setDisable(!PermissionManager.canPrintStockReport(UserService.getCurrentUserPermissions()));
        saveChanges.setDisable(!PermissionManager.canUpdateProducts(UserService.getCurrentUserPermissions()));

        if (PermissionManager.canActivateAccount(UserService.getCurrentUserPermissions())) {
            List<UserDTO> userToUpdate = loginController.getInactiveAccounts();

            if (!userToUpdate.isEmpty()) {

                Platform.runLater(() -> {
                    showActivateAccountWindow(ViewManager.getPrimaryStage(), userToUpdate);
                });
            }

        }


    }
}


