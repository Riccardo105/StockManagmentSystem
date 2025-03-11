package org.example.DTO;

import org.example.model.DTO.PaperBookDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaperBookDTOUnitTest implements ProductsTestsInterface<PaperBookDTO>{

    @Override
    public PaperBookDTO createTestProductUtility() {
        return new PaperBookDTO.Builder()
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
                .bindingType("Test binding type")
                .numPages(10)
                .edition("test edition")
                .build();
    }

    @Test
    @Override
    public void testValidObjectCreation() {
        PaperBookDTO paperBookDTO = createTestProductUtility();

        String expectedObject = "PaperBookDTO{title='Test title', stock=10, buyingPrice=10.0, " +
                "sellingPrice=10.0, format='Test format', author='Test Author', " +
                "publisher='Test publisher', genre='Test genre', series='Test series', " +
                "releaseDate=2023-10-15, bindingType='Test binding type', numPages=10, edition='test edition'}";

        assertEquals(expectedObject.trim(), paperBookDTO.toString());
    }

    @Test
    @Override
    public void testUpdateStock(){
        PaperBookDTO paperBookDTO = createTestProductUtility();
        paperBookDTO.updateStock(15);

        assertEquals(15, paperBookDTO.getStock());
    }

    @Test
    @Override
    public void testUpdateBuyingPrice(){
        PaperBookDTO paperBookDTO = createTestProductUtility();
        paperBookDTO.updateBuyingPrice(15);

        assertEquals(15, paperBookDTO.getBuyingPrice());
    }

    @Test
    @Override
    public void testUpdateSellingPrice(){
        PaperBookDTO paperBookDTO = createTestProductUtility();
        paperBookDTO.updateSellingPrice(15);

        assertEquals(15, paperBookDTO.getSellingPrice());
    }




}
