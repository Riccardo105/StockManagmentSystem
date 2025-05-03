package org.example.model.Service.products;

import com.google.common.util.concurrent.AbstractService;
import com.google.inject.Inject;
import org.example.model.DAO.products.CdDAO;
import org.example.model.DTO.products.CdDTO;
import org.example.model.DTO.products.CdDTO;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CdService extends AbstractProductsService<CdDTO> {


    @Inject
    public CdService() {
        this.requiredFields = Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "artist",
                "genre","releaseDate", "playTime", "tracksNum", "numOfDiscs",
                "conditions", "bitrateMbps");
    }

    public CdDTO createService(Map<String, Object> formData) {
        validateObjectCreation(formData);  // throws is validation fails

        return new CdDTO.Builder()
                .setTitle(formData.get("title").toString())
                .setBuyingPrice((float) formData.get("buyingPrice"))
                .setStock((int) formData.get("stock"))
                .setSellingPrice((float) formData.get("sellingPrice"))
                .setFormat("Cd")
                .setArtist(formData.get("artist").toString())
                .setLabel(formData.get("label") != null ? formData.get("label").toString() : null)
                .setGenre(formData.get("genre").toString())
                .setReleaseDate(Date.valueOf(formData.get("releaseDate").toString()))
                .setPlayTime(Time.valueOf(formData.get("playTime").toString()))
                .setTracksNum((Integer) formData.get("tracksNum"))
                .setNumOfDiscs((Integer) formData.get("numOfDiscs"))
                .setConditions(formData.get("conditions").toString())
                .setBitrateMbps((Integer) formData.get("bitrateMbps"))
                .build();

    }


}
