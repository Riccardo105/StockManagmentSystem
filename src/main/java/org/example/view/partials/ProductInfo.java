package org.example.view.partials;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.model.DTO.products.ProductDTO;
import org.example.model.Service.ObjectFileWriter;
import org.example.model.Service.PermissionManager;
import org.example.model.Service.UserService;
import org.example.view.DashboardView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class ProductInfo {

    public static Button printButton;

    public static VBox createProductView(ProductDTO product) {
        VBox container = new VBox(10);
        container.setStyle("-fx-padding: 15; -fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Get class name for header
        String className = product.getClass().getSimpleName().replace("DTO", "");
        Label titleLabel = new Label(className + " Details");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        titleLabel.setStyle("-fx-text-fill: #333333;");
        container.getChildren().add(titleLabel);

        // product image
        String imageFile =  switch (product.getClass().getSimpleName()) {
            case "AudioBookDTO" -> "audioBook.jpg";
            case "EBookDTO" -> "ebook.jpg";
            case "PaperBook" -> "paperBook.jpg";
            case "CdDTO" -> "cd.jpg";
            case "DigitalDTO" -> "digital.jpg";
            case "VinylDTO" -> "vinyl.jpeg";
            default -> throw new IllegalStateException("Unexpected value: " + product.getClass().getSimpleName());
        };

        Image productImage = new Image(Objects.requireNonNull(
                DashboardView.class.getResourceAsStream("/images/" + imageFile)
        ));

        ImageView imageView = new ImageView(productImage);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        container.getChildren().add(imageView);
        // Get all getter methods in inheritance order
        List<Method> allGetters = getInheritanceOrderedGetters(product.getClass());

        // Skip methods from Object class
        Set<String> objectMethods = new LinkedHashSet<>();
        Collections.addAll(objectMethods, "getClass", "hashCode", "toString", "equals", "notify", "notifyAll", "wait");

        // Create labels for each field
        for (Method getter : allGetters) {
            if (objectMethods.contains(getter.getName())) {
                continue; // Skip Object class methods
            }

            try {
                String fieldName = formatFieldName(getter.getName());
                Object value = getter.invoke(product);
                String valueStr = (value != null) ? value.toString() : "null";

                // Create bold text for field name
                Text fieldText = new Text(fieldName + ": ");
                fieldText.setFont(Font.font("System", FontWeight.BOLD, 14));
                fieldText.setFill(Color.web("#333333"));

                // Regular text for value
                Text valueText = new Text(valueStr);
                valueText.setFont(Font.font("System", FontWeight.NORMAL, 14));
                valueText.setFill(Color.web("#555555"));

                TextFlow textFlow = new TextFlow(fieldText, valueText);
                container.getChildren().add(textFlow);
            } catch (Exception e) {
                System.err.println("Error accessing field " + getter.getName() + ": " + e.getMessage());
            }
        }

        Label statusLabel = new Label();
        statusLabel.setFont(Font.font("System", FontWeight.NORMAL, 12));
        statusLabel.setTextFill(Color.DARKGREEN);

        printButton = new Button("Print Info");
        printButton.setDisable(!PermissionManager.canPrintProduct(UserService.getCurrentUserPermissions()));
        printButton.setFont(Font.font("System", FontWeight.BOLD, 14));
        printButton.setOnAction(e -> {
            try {
                ObjectFileWriter.writeObjectToFile("Product details", product);
                statusLabel.setText("Product info successfully saved to Desktop.");
                statusLabel.setTextFill(Color.DARKGREEN);
            } catch (IOException ex) {
                statusLabel.setText("Error saving file: " + ex.getMessage());
                statusLabel.setTextFill(Color.RED);
            }
        });

// Center the button in an HBox
        HBox buttonBox = new HBox( printButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        HBox statusBox = new HBox(statusLabel);
        statusBox.setAlignment(Pos.CENTER);

        container.getChildren().addAll(statusBox, buttonBox);

        return container;
    }

    private static List<Method> getInheritanceOrderedGetters(Class<?> clazz) {
        List<Method> getters = new ArrayList<>();

        // First get parent class methods
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass != Object.class) {
            getters.addAll(getInheritanceOrderedGetters(superClass));
        }

        // Then add current class methods
        getters.addAll(Arrays.stream(clazz.getDeclaredMethods())
                .filter(m -> m.getName().startsWith("get") && m.getParameterCount() == 0)
                .toList());

        return getters;
    }

    private static String formatFieldName(String getterName) {
        String fieldName = getterName.substring(3); // Remove "get"
        return fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1); // camelCase
    }

}
