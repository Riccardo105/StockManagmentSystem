package org.example.model.Service.products;

import com.google.inject.Inject;
import org.example.model.DAO.products.DigitalDAO;
import org.example.model.DTO.products.DigitalDTO;
import org.example.model.DTO.products.DigitalDTO;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DigitalService extends AbstractProductsService<DigitalDTO> {

    @Inject
    public DigitalService() {
        this.requiredFields = Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "artist",
                "genre", "releaseDate", "playTime", "tracksNum", "fileFormat", "fileSize", "bitrateMbps");
    }

    public DigitalDTO createService(Map<String, Object> formData) {
        validateObjectCreation(formData);  // throws is validation fails

        return new DigitalDTO.Builder()
                .setTitle(formData.get("title").toString())
                .setBuyingPrice((float) formData.get("buyingPrice"))
                .setStock((int) formData.get("stock"))
                .setSellingPrice((float) formData.get("sellingPrice"))
                .setFormat("Digital")
                .setArtist(formData.get("artist").toString())
                .setLabel(formData.get("label") != null ? formData.get("label").toString() : null)
                .setGenre(formData.get("genre").toString())
                .setReleaseDate(Date.valueOf(formData.get("releaseDate").toString()))
                .setPlayTime(Time.valueOf(formData.get("playTime").toString()))
                .setTracksNum((Integer) formData.get("tracksNum"))
                .setFileFormat(formData.get("fileFormat").toString())
                .setFileSize((float) formData.get("fileSize"))
                .setBitrateMbps((Integer) formData.get("bitrateMbps"))
                .build();

    }
}
