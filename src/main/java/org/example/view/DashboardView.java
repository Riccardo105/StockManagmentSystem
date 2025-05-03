package org.example.view;

import com.google.inject.Inject;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.ProductType;
import org.example.config.ObjectCreationException;
import org.example.controller.DashBoardController;
import org.example.view.partials.ProductFormBuilder;
import org.example.view.partials.ProductInfo;
import org.example.view.partials.ProductInfoPreview;
import org.example.view.partials.SearchBar;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class DashboardView extends VBox {

    private final DashBoardController dashBoardController;
    private final Map<String, String> errorMap = new HashMap<>();

    @Inject
    public DashboardView(DashBoardController controller) {

        this.dashBoardController = controller;
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        //product table
        ProductInfoPreview productsPreview = new ProductInfoPreview();

        // search bar
        // callback points to productPreview method
        HBox searchBar = new SearchBar(controller, productsPreview::updateTableEntries);

        // create new button
        ComboBox<ProductType> createNew = new ComboBox<>();
        createNew.getItems().addAll(ProductType.values());
        createNew.setPromptText("Create new");
        createNew.setOnAction(event -> {
            ProductType selectedProduct = createNew.getValue();
            if (selectedProduct != null) {
                showCreateForm(selectedProduct);
            }
        });

        // print stock report button
        Button stockReport = new Button("Stock Report");

        HBox controlPanel = new HBox(stockReport, createNew);
        HBox.setMargin(stockReport, new Insets(0, 10, 0, 0));
        controlPanel.setAlignment(Pos.TOP_RIGHT);
        controlPanel.setMaxWidth(Double.MAX_VALUE);

        VBox.setVgrow(controlPanel, Priority.ALWAYS);


        Button saveChanges = new Button("Save changes");

        getChildren().addAll(searchBar, controlPanel, productsPreview, saveChanges);

    }

    /**
     * this method manages the creation of ProductFormBuilder partial
     *
     * @param productType given by the "create new" drop-down menu
     */
    private void showCreateForm(ProductType productType) {

        Stage createProductStage = new Stage();
        createProductStage.setTitle("Create New " + productType.name());

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
        createProductStage.show();
    }

    /**
     * this method is used to map the discovered errors back to the form field.
     * this si achieved by keeping track of the field name
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
            return;
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
     * @param productInfo the product info window created in the preview table
     */
    public static void showProductDetailWindow(ProductInfo productInfo) {

    }
}


