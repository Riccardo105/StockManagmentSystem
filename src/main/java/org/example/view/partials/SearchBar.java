package org.example.view.partials;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchBar extends HBox {

    public SearchBar() {
        setSpacing(10);
        setPadding(new Insets(10));
        setAlignment(Pos.CENTER_LEFT);

        ComboBox<String> category = new ComboBox<>();
        category.setPromptText("Category");

        ComboBox<String> subCategory = new ComboBox<>();
        subCategory.setPromptText("Sub category");
        subCategory.setDisable(true);

        Map<String, List<String>> subcategoriesMap = Map.of(
            "Books", List.of("Ebook", "Paper Book", "Audiobook"),
            "Music", List.of("Cd", "Digital", "Vinyl")
        );

        category.getItems().addAll(subcategoriesMap.keySet());

        category.setOnAction(e -> {
            String selectedCategory = category.getValue();
            subCategory.getItems().clear();
            subCategory.getItems().addAll(subcategoriesMap.get(selectedCategory));
            subCategory.setDisable(false);
        });

        TextField searchPrompt = new TextField();
        searchPrompt.setPromptText("Search by title, Artist or Author...");
        HBox.setHgrow(searchPrompt, Priority.ALWAYS);

        Button searchButton = new Button("Search");

    getChildren().addAll(category, subCategory, searchPrompt, searchButton);

    }
}
