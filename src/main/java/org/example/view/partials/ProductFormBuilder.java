package org.example.view.partials;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.example.ProductType;
import org.example.model.DTO.products.*;


import java.lang.reflect.Field;
import java.util.*;

public class ProductFormBuilder {

    /**
     * formMap used to store dynamically generate form fields for retrieval in UI.
     * current fields vary according to product being created, determined by the create new menu in DashboardView
     */
    public static Map<String, Control> fieldMap = new HashMap<>();

    private static ScrollPane formBuilder(Class<? extends ProductDTO> productClass) {
        fieldMap.clear();

        VBox form = new VBox(10);

        form.setPadding(new Insets(15));
        form.setSpacing(10);

        Class<?> currentClass = productClass;

        List<Class<?>> classHierarchy = new ArrayList<>();
        while (currentClass != null) {
            classHierarchy.add(currentClass);
            currentClass = currentClass.getSuperclass();
        }

        // reverse list so that it starts adding from the top most class in the hierarchy
        Collections.reverse(classHierarchy);


        for (Class<?> clazz : classHierarchy) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);

                switch (field.getName()) {
                    case "id", "format" -> {
                        continue;
                    }
                    case "releaseDate" -> {
                        DatePicker datePicker = new DatePicker();
                        fieldMap.put(field.getName(), datePicker);
                        form.getChildren().addAll(
                                new Label(field.getName()),
                                datePicker
                        );
                        continue;
                    }
                }
                TextField textField = new TextField();
                fieldMap.put(field.getName(), textField);

                form.getChildren().addAll(
                        new Label(field.getName()),
                        textField
                );
            }
        }

        ScrollPane scrollPane = new ScrollPane(form);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxHeight(400);

        return scrollPane;
    }


    /**
     *
     * @param productType this is the selected product from the "create new" drop-down menu in the dashboardView
     * @return the product form dynamically created with the corresponding fields to the selected product
     */
    public static ScrollPane ProductFormManager(ProductType productType) {

        switch (productType){
            case Ebook -> {
                return formBuilder(EBookDTO.class);
            }
            case PaperBook -> {
                return formBuilder(PaperBookDTO.class);
            }
            case AudioBook -> {
                return formBuilder(AudioBookDTO.class);
            }
            case Cd -> {
                return formBuilder(CdDTO.class);
            }
            case Digital -> {
                return formBuilder(DigitalDTO.class);
            }
            case Vinyl -> {
                return formBuilder(VinylDTO.class);
            }

        }
        return null;
    }
}