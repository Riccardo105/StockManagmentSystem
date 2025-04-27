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
    EBookDAO ebookDAO;

    @Inject
    public EbookService(EBookDAO ebookDAO) {
        this.ebookDAO = ebookDAO;
        this.requiredFields= Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "format", "author",
                "genre", "releaseDate", "numPages", "fileSize", "fileFormat");
    }



    public void createService(Map<String, Object> formData) {
        validateObjectCreation(formData);
        EBookDTO eBookDTO = new EBookDTO.Builder()
                .setTitle(formData.get("title").toString())
                .setBuyingPrice((float) formData.get("buyingPrice"))
                .setStock((int) formData.get("stock"))
                .setSellingPrice((float) formData.get("sellingPrice"))
                .setFormat(formData.get("format").toString())
                .setAuthor(formData.get("author").toString())
                .setPublisher(formData.get("publisher").toString())
                .setGenre(formData.get("genre").toString())
                .setSeries(formData.get("series").toString())
                .setReleaseDate(Date.valueOf(formData.get("date").toString()))
                .setNumPages((Integer) formData.get("numPages"))
                .setFileSize((float) formData.get("fileSize"))
                .setFileFormat(formData.get("fileFormat").toString())
                .setNumPages((int) formData.get("numPages"))
                .build();

        try {
            ebookDAO.create(eBookDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating audio book", e);
        }
    }


    /**
     *
     * @param productsToUpdate products the user as modified
     * @return list of invalid objects, so that the UI can identify the ones and show them to the user
     */
    public List<EBookDTO> updateService(List<EBookDTO> productsToUpdate) {
        List<EBookDTO> invalidItems = new ArrayList<>();

        for (EBookDTO dto: productsToUpdate){
            if (!validatePrice(dto.getSellingPrice(), dto.getBuyingPrice()) || !validateStockLevel(dto.getStock())) {
                productsToUpdate.remove(dto);
                invalidItems.add(dto);
            }
        }

        try {
            ebookDAO.update(productsToUpdate);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during update operation", e);
        }

        return invalidItems;
    }

    public void deleteService(List<EBookDTO> productsToDelete) {
        try {
            ebookDAO.delete(productsToDelete);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during delete operation", e);
        }
    }
}
