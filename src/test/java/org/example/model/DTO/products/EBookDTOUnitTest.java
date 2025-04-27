package org.example.model.DTO.products;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EBookDTOUnitTest implements ProductsTestsInterface<EBookDTO> {

    @Override
    public EBookDTO createTestProductUtility() {
        return new EBookDTO.Builder()
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
    }
    @Test
    @Override
    public void testValidObjectCreation(){
        EBookDTO ebook = createTestProductUtility();

        String expectedObject = "EBookDTO{title='Test title', stock=10, buyingPrice=10.0, " +
                "sellingPrice=10.0, format='Test format', author='Test Author', " +
                "publisher='Test publisher', genre='Test genre', series='Test series', " +
                "releaseDate=2023-10-15, fileSize=10.0, fileFormat='Test File Format', numPages=10}";

        assertEquals(expectedObject.trim(), ebook.toString());
    }



    @Test
    @Override
    public void testUpdateStock() {
        EBookDTO ebook = createTestProductUtility();
        ebook.updateStock(15);

        assertEquals(15, ebook.getStock());
    }

    @Test
    @Override
    public void testUpdateBuyingPrice() {
        EBookDTO ebook = createTestProductUtility();
        ebook.updateBuyingPrice(15.0f);

        assertEquals(15.0f, ebook.getBuyingPrice());
    }

    @Test
    @Override
    public void testUpdateSellingPrice() {
        EBookDTO ebook = createTestProductUtility();
        ebook.updateSellingPrice(15.0f);

        assertEquals(15.0f, ebook.getSellingPrice());
    }
}
