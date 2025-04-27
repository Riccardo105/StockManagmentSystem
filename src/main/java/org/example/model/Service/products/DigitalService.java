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
    DigitalDAO digitalDAO;

    @Inject
    public DigitalService(DigitalDAO digitalDAO) {
        this.digitalDAO = digitalDAO;
        this.requiredFields = Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "format", "artist",
                "genre", "releaseDate", "playTime", "tracksNum", "fileFormat", "FileSize", "BitrateMbps");
    }

    public void createService(Map<String, Object> formData) {
        validateObjectCreation(formData);
        DigitalDTO digitalDTO = new DigitalDTO.Builder()
                .setTitle(formData.get("title").toString())
                .setBuyingPrice((float) formData.get("buyingPrice"))
                .setStock((int) formData.get("stock"))
                .setSellingPrice((float) formData.get("sellingPrice"))
                .setFormat(formData.get("format").toString())
                .setArtist(formData.get("artist").toString())
                .setLabel(formData.get("label").toString())
                .setGenre(formData.get("genre").toString())
                .setReleaseDate(Date.valueOf(formData.get("releaseDate").toString()))
                .setPlayTime(Time.valueOf(formData.get("playTime").toString()))
                .setTracksNum((Integer) formData.get("tracksNum"))
                .setFileFormat(formData.get("fileFormat").toString())
                .setFileSize((float) formData.get("fileSize"))
                .setBitrateMbps((Integer) formData.get("bitrateMbps"))
                .build();

        try {
            digitalDAO.create(digitalDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating audio book", e);
        }
    }


    /**
     *
     * @param productsToUpdate products the user as modified
     * @return list of invalid objects, so that the UI can identify the ones and show them to the user
     */
    public List<DigitalDTO> updateService(List<DigitalDTO> productsToUpdate) {
        List<DigitalDTO> invalidItems = new ArrayList<>();

        for (DigitalDTO dto: productsToUpdate){
            if (!validatePrice(dto.getSellingPrice(), dto.getBuyingPrice()) || !validateStockLevel(dto.getStock())) {
                productsToUpdate.remove(dto);
                invalidItems.add(dto);
            }
        }

        try {
            digitalDAO.update(productsToUpdate);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during update operation", e);
        }

        return invalidItems;
    }

    public void deleteService(List<DigitalDTO> productsToDelete) {
        try {
            digitalDAO.delete(productsToDelete);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during delete operation", e);
        }
    }
}
