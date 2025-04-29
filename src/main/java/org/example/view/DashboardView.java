package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.ProductType;
import org.example.view.partials.ProductFormBuilder;
import org.example.view.partials.ProductInfo;
import org.example.view.partials.ProductInfoPreview;
import org.example.view.partials.SearchBar;


public class DashboardView extends VBox {

    public DashboardView() {
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.setAlignment(Pos.TOP_CENTER);

        // search bar
        HBox searchBar = new SearchBar();

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
        HBox.setMargin(stockReport, new Insets(0, 10, 0, 0) );
        controlPanel.setAlignment(Pos.TOP_RIGHT);
        controlPanel.setMaxWidth(Double.MAX_VALUE);

        VBox.setVgrow(controlPanel, Priority.ALWAYS);

        VBox productsPreview = new ProductInfoPreview();

        Button saveChanges = new Button("Save changes");

        getChildren().addAll(searchBar, controlPanel, productsPreview, saveChanges);

    }

    private void showCreateForm(ProductType productType) {
        Stage createProductStage = new Stage();
        createProductStage.setTitle("Create New " + productType.name());

        ScrollPane form = ProductFormBuilder.ProductFormManager(productType);

        Button createButton = new Button("Create");

        HBox buttonContainer = new HBox(createButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        VBox formContainer = new VBox(20);
        formContainer.getChildren().addAll(form, buttonContainer);
        formContainer.setPadding(new Insets(20));


        Scene createProductScene = new Scene(formContainer, 400, 400);

        createProductStage.setScene(createProductScene);
        createProductStage.show();
    }

    public static void showProductDetailWindow(ProductInfo productInfo) {

    }
}
