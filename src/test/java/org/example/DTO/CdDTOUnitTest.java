package org.example.DTO;

import org.example.model.DTO.CdDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CdDTOUnitTest implements ProductsTestsInterface<CdDTO>{

    public CdDTO createTestProductUtility(){
        return new CdDTO.Builder()
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
                .setNumOfDiscs(10)
                .setConditions("Test condition")
                .setBitrateMbps(10)
                .build();

    }
    @Test
    @Override
    public void testValidObjectCreation(){
        CdDTO cdDTO = createTestProductUtility();

        String expectedObject = "CdDTO{" + "title='Test title', " + "stock=10, " + "buyingPrice=10.0, " +
                "sellingPrice=10.0, " + "format='Test format', " + "artist='Test artist', " + "label='Test label', " +
                "genre='Test genre', " + "releaseDate=2023-10-15, " + "playTime=01:10:00, " + "tracksNum=10, " +
                "numOfDiscs=10, " + "conditions='Test condition', " + "bitrateMbps=10" + "}";

        assertEquals(expectedObject.trim(), cdDTO.toString());
    }

    @Test
    @Override
    public void testUpdateStock(){
        CdDTO cdDTO = createTestProductUtility();
        cdDTO.updateStock(15);

        assertEquals(15, cdDTO.getStock());
    }

    @Test
    @Override
    public void testUpdateBuyingPrice(){
        CdDTO cdDTO = createTestProductUtility();
        cdDTO.updateBuyingPrice(15);

        assertEquals(15, cdDTO.getBuyingPrice());
    }

    @Test
    @Override
    public void testUpdateSellingPrice(){
        CdDTO cdDTO = createTestProductUtility();
        cdDTO.updateSellingPrice(15);

        assertEquals(15, cdDTO.getSellingPrice());
    }
}
