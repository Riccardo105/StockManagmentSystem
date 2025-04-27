package org.example.model.DTO.products;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AudioBookDTOUnitTest implements ProductsTestsInterface<AudioBookDTO> {

    public AudioBookDTO createTestProductUtility() {
        return new AudioBookDTO.Builder()
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
    }

    @Test
    @Override
    public void testValidObjectCreation() {
        AudioBookDTO audioBookDTO = createTestProductUtility();

        String expectedObject = "AudioBookDTO{title='Test title', stock=10, buyingPrice=10.0, " +
                "sellingPrice=10.0, format='Test format', author='Test Author', " +
                "publisher='Test publisher', genre='Test genre', series='Test series', " +
                "releaseDate=2023-10-15, narrator='test narrator', fileSize=10.0, fileFormat='Test File Format'}";

        assertEquals(expectedObject.trim(), audioBookDTO.toString());
    }

    @Test
    @Override
    public void testUpdateStock() {
        AudioBookDTO audioBookDTO = createTestProductUtility();
        audioBookDTO.updateStock(15);

        assertEquals(15, audioBookDTO.getStock());
    }

    @Test
    @Override
    public void testUpdateBuyingPrice() {
        AudioBookDTO audioBookDTO = createTestProductUtility();
        audioBookDTO.updateBuyingPrice(15);

        assertEquals(15, audioBookDTO.getBuyingPrice());
    }

    @Test
    @Override
    public void testUpdateSellingPrice() {
        AudioBookDTO audioBookDTO = createTestProductUtility();
        audioBookDTO.updateSellingPrice(15);

        assertEquals(15, audioBookDTO.getSellingPrice());
    }


}
