package org.example.model.Service.products;

import com.google.inject.Inject;
import org.example.config.ObjectCreationException;
import org.example.model.DAO.products.AudioBookDAO;
import org.example.model.DTO.products.AudioBookDTO;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AudioBookService extends AbstractProductsService<AudioBookDTO> {


    @Inject
    public AudioBookService() {

        this.requiredFields = Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "author",
                "genre", "releaseDate", "narrator", "fileSize", "fileFormat");
    }

    public AudioBookDTO createService(Map<String, Object> formData) {
            try {
            validateObjectCreation(formData);

                return new AudioBookDTO.Builder()
                        .setTitle(formData.get("title").toString())
                        .setBuyingPrice((float) formData.get("buyingPrice"))
                        .setStock((int) formData.get("stock"))
                        .setSellingPrice((float) formData.get("sellingPrice"))
                        .setFormat("Audio Book")
                        .setAuthor(formData.get("author").toString())
                        .setPublisher(formData.get("publisher") != null ? formData.get("publisher").toString() : null)
                        .setGenre(formData.get("genre").toString())
                        .setSeries(formData.get("series") != null ? formData.get("series").toString() : null)
                        .setReleaseDate(Date.valueOf(formData.get("releaseDate").toString()))
                        .setNarrator(formData.get("narrator").toString())
                        .setFileSize((float) formData.get("fileSize"))
                        .setFileFormat(formData.get("fileFormat").toString())
                        .build();

            } catch (ObjectCreationException e) {
                throw new ObjectCreationException(e.getErrorMap());
            }
    }


}
