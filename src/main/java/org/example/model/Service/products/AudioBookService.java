package org.example.model.Service.products;

import com.google.inject.Inject;
import org.example.model.DAO.products.AudioBookDAO;
import org.example.model.DTO.products.AudioBookDTO;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AudioBookService extends AbstractProductsService<AudioBookDTO> {

    private final AudioBookDAO audioBookDAO;

    @Inject
    public AudioBookService(AudioBookDAO audioBookDAO) {
        this.audioBookDAO = audioBookDAO;
        this.requiredFields = Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "format", "author",
                "genre", "releaseDate", "narrator", "fileSize", "fileFormat");
    }

    public void createService(Map<String, Object> formData) {
            validateObjectCreation(formData);
            AudioBookDTO audioBookDTO = new AudioBookDTO.Builder()
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
                    .setNarrator(formData.get("narrator").toString())
                    .setFileSize((float) formData.get("fileSize"))
                    .setFileFormat(formData.get("fileFormat").toString())
                    .build();

            try {
                audioBookDAO.create(audioBookDTO);
            } catch (Exception e) {
                throw new RuntimeException("Error while creating audio book", e);
            }
    }


    /**
     *
     * @param productsToUpdate products the user as modified
     * @return list of invalid objects, so that the UI can identify the ones and show them to the user
     */
    public List<AudioBookDTO> updateService(List<AudioBookDTO> productsToUpdate) {
        List<AudioBookDTO> invalidItems = new ArrayList<>();

        for (AudioBookDTO dto: productsToUpdate){
            if (!validatePrice(dto.getSellingPrice(), dto.getBuyingPrice()) || !validateStockLevel(dto.getStock())) {
                productsToUpdate.remove(dto);
                invalidItems.add(dto);
            }
        }

        try {
            audioBookDAO.update(productsToUpdate);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during update operation", e);
        }

        return invalidItems;
    }

    public void deleteService(List<AudioBookDTO> productsToDelete) {
        try {
            audioBookDAO.delete(productsToDelete);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during delete operation", e);
        }
    }


}
