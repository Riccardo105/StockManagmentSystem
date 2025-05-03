package org.example.model.Service.products;

import com.google.inject.Inject;
import org.example.model.DAO.products.VinylDAO;
import org.example.model.DTO.products.VinylDTO;
import org.example.model.DTO.products.VinylDTO;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VinylService extends AbstractProductsService<VinylDTO>{


    @Inject
    public VinylService() {
        this.requiredFields = Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "artist",
                "genre", "releaseDate", "playTime", "tracksNum", "rpm", "size", "edition");
    }

    public VinylDTO createService(Map<String, Object> formData) {
        validateObjectCreation(formData);  // throws is validation fails

        return new VinylDTO.Builder()
                .setTitle(formData.get("title").toString())
                .setBuyingPrice((float) formData.get("buyingPrice"))
                .setStock((int) formData.get("stock"))
                .setSellingPrice((float) formData.get("sellingPrice"))
                .setFormat("vinyl")
                .setArtist(formData.get("artist").toString())
                .setLabel(formData.get("label") != null ? formData.get("label").toString() : null)
                .setGenre(formData.get("genre").toString())
                .setReleaseDate(Date.valueOf(formData.get("releaseDate").toString()))
                .setPlayTime(Time.valueOf(formData.get("playTime").toString()))
                .setTracksNum((Integer) formData.get("tracksNum"))
                .setRpm((Integer) formData.get("rpm"))
                .setSize((Integer) formData.get("size"))
                .setEdition(formData.get("edition").toString())
                .build();
    }
}
