package org.example.model.Service;

import org.example.model.DTO.products.ProductDTO;
import org.example.model.DTO.products.ProductWrapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ObjectFileWriter {

    public static void writeObjectToFile(String fileName, Object object) throws IOException {
        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        File file = new File(desktopPath, fileName);

        try {
            boolean isNewFile = file.createNewFile();
            if (!isNewFile && !file.exists()) {
                throw new IOException("File creation failed unexpectedly");
            }
        } catch (IOException e) {
            throw new IOException("File operation failed: " + e.getMessage(), e);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            // Write formatted object info
            bw.write("========== Product Info ==========");
            bw.newLine();

            // Use reflection to get fields from getter methods
            List<Method> getters = Arrays.stream(object.getClass().getMethods())
                    .filter(m -> m.getName().startsWith("get") && m.getParameterCount() == 0 && !m.getName().equals("getClass"))
                    .toList();

            for (Method getter : getters) {
                try {
                    String fieldName = formatFieldName(getter.getName());
                    Object value = getter.invoke(object);
                    String valueStr = (value != null) ? value.toString() : "null";
                    bw.write(String.format("%-25s : %s", fieldName, valueStr));
                    bw.newLine();
                } catch (Exception e) {
                    System.err.println("Error accessing field " + getter.getName() + ": " + e.getMessage());
                }
            }

            bw.write("==================================");
            System.out.println("Product info successfully saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println( e.getMessage());
            e.printStackTrace();
            throw new IOException("File operation failed: " + e.getMessage(), e);
        }
    }

    // Helper method to format field names
    private static String formatFieldName(String getterName) {
        String fieldName = getterName.substring(3);
        return fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static void writeStockReportToFile(List<ProductDTO> products) throws IOException {
        ArrayList<ProductWrapper> productsToWrite = new ArrayList<>();

        for (ProductDTO product : products) {
            productsToWrite.add(
                    new ProductWrapper(product.getTitle(), product.getStock(), product.calculateStockBuyingCost(),
                                       product.calculateStockSellingValue()));
        }

        double totalStockBuyingCost = 0;
        double totalStockSellingValue = 0;

        for (ProductWrapper productWrapper : productsToWrite) {
            totalStockBuyingCost += productWrapper.getStockBuyingCost();
            totalStockSellingValue += productWrapper.getStockSellingValue();
        }

        String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
        File file = new File(desktopPath, "stock_report");

        try {
            boolean isNewFile = file.createNewFile();
            if (!isNewFile && !file.exists()) {
                //Handles logical exception during file creation
                throw new IOException("File creation failed unexpectedly");
            }
        } catch (IOException e) {
            //Handles system level I/O errors
            throw new IOException("File operation failed: " + e.getMessage(), e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Write header
            writer.write(String.format("%-40s %10s", "Product Name", "Stock"));
            writer.newLine();
            writer.write("---------------------------------------- ----------");
            writer.newLine();

            // Write each product
            for (ProductWrapper product : productsToWrite) {
                String line = String.format("%-40s %10d",
                        product.getName(),
                        product.getStock());
                writer.write(line);
                writer.newLine();
            }

            // Write totals
            writer.newLine();
            writer.write(String.format("%-40s %10.2f", "Total Stock buying cost:", totalStockBuyingCost));
            writer.newLine();
            writer.write(String.format("%-40s %10.2f", "Total Stock Selling value:", totalStockSellingValue));

            System.out.println("Stock report successfully saved to: " + file.getAbsolutePath());
        }catch (IOException e) {
            throw new IOException("File operation failed: " + e.getMessage(), e);
        }

    }
}


