package org.example.model.Service.products;

import com.google.inject.Inject;
import org.example.model.DAO.products.EBookDAO;
import org.example.model.DAO.products.PaperBookDAO;
import org.example.model.DTO.products.PaperBookDTO;
import org.example.model.DTO.products.PaperBookDTO;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PaperBookService extends AbstractProductsService<PaperBookDTO> {

    @Inject
    public PaperBookService() {
        this.requiredFields= Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "author",
                "genre", "releaseDate", "numPages","bindingType", "edition");
    }



    public PaperBookDTO createService(Map<String, Object> formData) {
        validateObjectCreation(formData);  // throws is validation fails

        return new PaperBookDTO.Builder()
                .setTitle(formData.get("title").toString())
                .setBuyingPrice((float) formData.get("buyingPrice"))
                .setStock((int) formData.get("stock"))
                .setSellingPrice((float) formData.get("sellingPrice"))
                .setFormat("Paper book")
                .setAuthor(formData.get("author").toString())
                .setPublisher(formData.get("publisher") != null ? formData.get("publisher").toString() : null)
                .setGenre(formData.get("genre").toString())
                .setSeries(formData.get("series") != null ? formData.get("series").toString() : null)
                .setReleaseDate(Date.valueOf(formData.get("releaseDate").toString()))
                .setBindingType(formData.get("bindingType").toString())
                .setNumPages((Integer) formData.get("numPages"))
                .setEdition(formData.get("edition").toString())
                .build();

    }

}
