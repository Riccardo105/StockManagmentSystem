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
    PaperBookDAO paperBookDAO;

    @Inject
    public PaperBookService(PaperBookDAO paperBookDAO) {
        this.paperBookDAO = paperBookDAO;
        this.requiredFields= Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "format", "author",
                "genre", "releaseDate", "numPages","bindingType", "edition");
    }



    public void createService(Map<String, Object> formData) {
        validateObjectCreation(formData);
        PaperBookDTO paperBookDTO = new PaperBookDTO.Builder()
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
                .setBindingType(formData.get("bindingType").toString())
                .setNumPages((Integer) formData.get("numPages"))
                .setEdition(formData.get("edition").toString())
                .build();

        try {
            paperBookDAO.create(paperBookDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating audio book", e);
        }
    }


    /**
     *
     * @param productsToUpdate products the user as modified
     * @return list of invalid objects, so that the UI can identify the ones and show them to the user
     */
    public List<PaperBookDTO> updateService(List<PaperBookDTO> productsToUpdate) {
        List<PaperBookDTO> invalidItems = new ArrayList<>();

        for (PaperBookDTO dto: productsToUpdate){
            if (!validatePrice(dto.getSellingPrice(), dto.getBuyingPrice()) || !validateStockLevel(dto.getStock())) {
                productsToUpdate.remove(dto);
                invalidItems.add(dto);
            }
        }

        try {
            paperBookDAO.update(productsToUpdate);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during update operation", e);
        }

        return invalidItems;
    }

    public void deleteService(List<PaperBookDTO> productsToDelete) {
        try {
            paperBookDAO.delete(productsToDelete);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during delete operation", e);
        }
    }
}
