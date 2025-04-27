package org.example.model.service;

import org.example.model.DTO.products.AudioBookDTO;
import org.example.model.DTO.products.EBookDTO;
import org.example.model.DTO.products.ProductDTO;
import org.example.model.Service.ObjectFileWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ObjectFileWriterUnitTest {

    @Test
    public void testWriteObjectToFile() {
        AudioBookDTO obj = new AudioBookDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setAuthor("Test Author")
                .setPublisher("Test publisher")
                .setGenre("Test genre")
                .setSeries("Test series")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setNarrator("test narrator")
                .setFileSize(10.0f)
                .setFileFormat("Test File Format")
                .build();
        String filePath = "Product_info";

        try {
            ObjectFileWriter.writeObjectToFile(filePath, obj);
        } catch(IOException e) {
            e.printStackTrace();
        }

        assertTrue(Files.exists(Paths.get("C:\\Users\\User\\Desktop\\Product_info")));

    }

    @Test
    public void testWriteStockReport() {
        EBookDTO ebook = new EBookDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setAuthor("Test Author")
                .setPublisher("Test publisher")
                .setGenre("Test genre")
                .setSeries("Test series")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setFileSize(10.0f)
                .setFileFormat("Test File Format")
                .setNumPages(10)
                .build();

        ArrayList<ProductDTO> listOfProducts = new ArrayList<>();
        listOfProducts.add(ebook);

        try {
            ObjectFileWriter.writeStockReportToFile(listOfProducts);
        } catch(IOException e) {
            e.printStackTrace();
        }

        assertTrue(Files.exists(Paths.get("C:\\Users\\User\\Desktop\\stock_report")));
    }
}
