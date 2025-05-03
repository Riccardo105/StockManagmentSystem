package org.example.model.Service.products;

import com.google.inject.Inject;
import org.example.model.DAO.products.EBookDAO;
import org.example.model.DTO.products.EBookDTO;
import org.example.model.DTO.products.EBookDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EbookService extends AbstractProductsService<EBookDTO> {

    @Inject
    public EbookService() {

        this.requiredFields= Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "author",
                "genre", "releaseDate", "numPages", "fileSize", "fileFormat");
    }



    public EBookDTO createService(Map<String, Object> formData) {
        validateObjectCreation(formData);  // throws is validation fails

        return new EBookDTO.Builder()
                .setTitle(formData.get("title").toString())
                .setBuyingPrice((float) formData.get("buyingPrice"))
                .setStock((int) formData.get("stock"))
                .setSellingPrice((float) formData.get("sellingPrice"))
                .setFormat("Ebook")
                .setAuthor(formData.get("author").toString())
                .setPublisher(formData.get("publisher") != null ? formData.get("publisher").toString() : null)
                .setGenre(formData.get("genre").toString())
                .setSeries(formData.get("series") != null ? formData.get("series").toString() : null)
                .setReleaseDate(Date.valueOf(formData.get("releaseDate").toString()))
                .setNumPages((Integer) formData.get("numPages"))
                .setFileSize((float) formData.get("fileSize"))
                .setFileFormat(formData.get("fileFormat").toString())
                .setNumPages((int) formData.get("numPages"))
                .build();
    }
}
