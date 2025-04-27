package org.example.view.partials;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.ProductType;
import org.example.model.DTO.products.*;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductFormBuilder {

    private static ScrollPane formBuilder(Class<? extends ProductDTO> productClass) {
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

                if ("id".equals(field.getName())) {
                    continue;
                }

                form.getChildren().addAll(
                        new Label(field.getName()),
                        new TextField()
                );
            }
        }

        ScrollPane scrollPane = new ScrollPane(form);
        scrollPane.setFitToWidth(true);
        scrollPane.setMaxHeight(400);

        return scrollPane;
    }



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