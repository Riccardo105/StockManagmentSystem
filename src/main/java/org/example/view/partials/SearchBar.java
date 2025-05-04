package org.example.view.partials;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import org.example.ProductType;
import org.example.controller.DashBoardController;
import org.example.model.DTO.products.ProductDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SearchBar extends HBox {

    private final DashBoardController dashboardController;
    private final ComboBox<String> category;
    private final ComboBox<String> subCategory;
    private final TextField searchPrompt;

    // consumer is set in the dashboardView, and it's used to reference ProductView.updateTableEntries()
    public SearchBar(DashBoardController controller, Consumer<List<ProductDTO>> callback) {
        this.dashboardController = controller;

        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER_LEFT);

        category = new ComboBox<>();
        category.setPromptText("Category");

        subCategory = new ComboBox<>();
        subCategory.setPromptText("Sub category");
        subCategory.setDisable(true);

        Map<String, List<String>> subcategoriesMap = Map.of(
            "Book", List.of("Ebook", "Paper Book", "Audiobook"),
            "Music", List.of("Cd", "Digital", "Vinyl")
        );

        category.getItems().addAll(subcategoriesMap.keySet());

        // subcategory menu dynamically updates based on category
        category.setOnAction(e -> {
            String selectedCategory = category.getValue();
            subCategory.getItems().clear();
            subCategory.getItems().addAll(subcategoriesMap.get(selectedCategory));
            subCategory.setDisable(false);
        });

        searchPrompt = new TextField();
        searchPrompt.setPromptText("Search by title, Artist or Author...");
        HBox.setHgrow(searchPrompt, Priority.ALWAYS);

        Button searchButton = createButton(callback);

        getChildren().addAll(category, subCategory, searchPrompt, searchButton);

    }

    private Button createButton(Consumer<List<ProductDTO>> callback) {
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {

            Map<Node, String> errorMap = new HashMap<>();
            // refresh errors upon new search prompt
            clearErrorStyling();

            // 1. Mandatory: category
            ProductType.Category categoryType = null;
            if (category.getValue() == null) {
                errorMap.put(category, "Category must be selected");
            } else {categoryType = ProductType.Category.valueOf(category.getValue().toUpperCase());
            }


            // 2. Mandatory: search text
            String searchText = searchPrompt.getText();
            if (searchText == null || searchText.trim().isEmpty()) {
                errorMap.put(searchPrompt, "search prompt must not be empty");
            }

            // 3. Optional: sub-category
            ProductType subCatEnum = null;
            if (subCategory.getValue() != null) {
                try {
                    subCatEnum = ProductType.valueOf(subCategory.getValue().replace(" ", ""));
                } catch (IllegalArgumentException ex) {
                    System.out.println(" Invalid sub-category selected.");
                }
            }

            if (!errorMap.isEmpty()) {
                errorMap.forEach((k, v) -> {
                    setErrorStyling(k, v);
                });
                return;
            }
            // 4. Perform search + callback
            List<ProductDTO> results = dashboardController
                    .handleProductSearch(categoryType, subCatEnum, searchText);
            callback.accept(results);
        });
        return searchButton;
    }

    private void setErrorStyling(Node node, String errorMessage) {
        node.setStyle("-fx-border-color: red; -fx-border-width: 1px;");

        Tooltip.install(node, new Tooltip(errorMessage));
    }

    private void clearErrorStyling() {
        
        category.setStyle("");
        searchPrompt.setStyle("");
        subCategory.setStyle("");
        // Remove tooltips if they exist
        Tooltip.uninstall(category, category.getTooltip());
        Tooltip.uninstall(searchPrompt, searchPrompt.getTooltip());
        Tooltip.uninstall(subCategory, subCategory.getTooltip());
    }
}
