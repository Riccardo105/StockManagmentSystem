package org.example.model.DTO.products;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VinylDTOUnitTest implements ProductsTestsInterface<VinylDTO> {

    public VinylDTO createTestProductUtility(){
        return new VinylDTO.Builder()
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
                .setRpm(100)
                .setSize(10)
                .setEdition("Test edition")
                .build();
    }

    @Test
    @Override
    public void testValidObjectCreation() {
        VinylDTO vinylDTO = createTestProductUtility();

        String expectedObject = "CdDTO{" + "title='Test title', " + "stock=10, " + "buyingPrice=10.0, " +
                "sellingPrice=10.0, " + "format='Test format', " + "artist='Test artist', " + "label='Test label', " +
                "genre='Test genre', " + "releaseDate=2023-10-15, " + "playTime=01:10:00, " + "tracksNum=10, " +
                "rpm=100, " + "size=10, " + "edition='Test edition'}";

        assertEquals(expectedObject.trim(), vinylDTO.toString());
    }

    @Test
    @Override
    public void testUpdateStock(){
        VinylDTO vinylDTO = createTestProductUtility();
        vinylDTO.updateStock(15);

        assertEquals(15, vinylDTO.getStock());
    }

    @Test
    @Override
    public void testUpdateBuyingPrice(){
        VinylDTO vinylDTO = createTestProductUtility();
        vinylDTO.updateBuyingPrice(15);

        assertEquals(15, vinylDTO.getBuyingPrice());
    }

    @Test
    @Override
    public void testUpdateSellingPrice(){
        VinylDTO vinylDTO = createTestProductUtility();
        vinylDTO.updateSellingPrice(15);

        assertEquals(15, vinylDTO.getSellingPrice());
    }
}
