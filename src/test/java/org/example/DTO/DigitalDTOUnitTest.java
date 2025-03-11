package org.example.DTO;

import org.example.model.DTO.DigitalDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DigitalDTOUnitTest implements ProductsTestsInterface<DigitalDTO>{

    public DigitalDTO createTestProductUtility(){
        return new DigitalDTO.Builder()
                .setTitle("Test title")
                .setBuyingPrice(10.0f)
                .setStock(10)
                .setSellingPrice(10.0f)
                .setFormat("Test format")
                .setArtist("Test artist")
                .setLabel("Test label")
                .setGenre("Test genre")
                .setReleaseDate(java.sql.Date.valueOf("2023-10-15"))
                .setPlayTime(java.sql.Time.valueOf("01:10:00"))
                .setTracksNum(10)
                .setFileFormat("Test format")
                .setFileSize(100.0f)
                .setBitrateMbps(100)
                .build();
    }

    @Test
    @Override
    public void testValidObjectCreation(){
        DigitalDTO digitalDTO = createTestProductUtility();

        String expectedObject = "DigitalDTO{" + "title='Test title', " + "stock=10, " + "buyingPrice=10.0, " +
                "sellingPrice=10.0, " + "format='Test format', " + "artist='Test artist', " + "label='Test label', " +
                "genre='Test genre', " + "releaseDate=2023-10-15, " + "playTime=01:10:00, " + "tracksNum=10, " +
                "fileFormat='Test format', " + "fileSize=100.0, " + "bitrateMbps=100" + "}";

        assertEquals(expectedObject.trim(), digitalDTO.toString());
    }

    @Test
    @Override
    public void testUpdateStock(){
        DigitalDTO digitalDTO = createTestProductUtility();
        digitalDTO.updateStock(15);

        assertEquals(15, digitalDTO.getStock());
    }

    @Test
    @Override
    public void testUpdateBuyingPrice(){
        DigitalDTO digitalDTO = createTestProductUtility();
        digitalDTO.updateBuyingPrice(15);

        assertEquals(15, digitalDTO.getBuyingPrice());
    }

    @Test
    @Override
    public void testUpdateSellingPrice(){
        DigitalDTO digitalDTO = createTestProductUtility();
        digitalDTO.updateSellingPrice(15);

        assertEquals(15, digitalDTO.getSellingPrice());
    }
}
