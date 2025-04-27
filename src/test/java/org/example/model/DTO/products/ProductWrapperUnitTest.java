package org.example.model.DTO.products;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductWrapperUnitTest {

    @Test
    public void testValid() {
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

        ProductWrapper productWrapper = new ProductWrapper(ebook.getTitle(), ebook.getStock(), ebook.calculateStockBuyingCost(), ebook.calculateStockSellingValue());

        assertEquals(10, productWrapper.getStock());
        assertEquals(100.0, productWrapper.getStockBuyingCost());
        assertEquals(100.0, productWrapper.getStockSellingValue());
    }




}
