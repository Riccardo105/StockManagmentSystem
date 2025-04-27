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
    VinylDAO vinylDAO;

    @Inject
    public VinylService(VinylDAO vinylDAO) {
        this.vinylDAO = vinylDAO;
        this.requiredFields = Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "format", "artist",
                "genre", "numPages","releaseDate", "playTime", "tracksNum", "rpm", "size", "edition");
    }

    public void createService(Map<String, Object> formData) {
        validateObjectCreation(formData);
        VinylDTO vinylDTO = new VinylDTO.Builder()
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
                .setRpm((Integer) formData.get("rpm"))
                .setSize((Integer) formData.get("size"))
                .setEdition(formData.get("edition").toString())
                .build();

        try {
            vinylDAO.create(vinylDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating audio book", e);
        }
    }


    /**
     *
     * @param productsToUpdate products the user as modified
     * @return list of invalid objects, so that the UI can identify the ones and show them to the user
     */
    public List<VinylDTO> updateService(List<VinylDTO> productsToUpdate) {
        List<VinylDTO> invalidItems = new ArrayList<>();

        for (VinylDTO dto: productsToUpdate){
            if (!validatePrice(dto.getSellingPrice(), dto.getBuyingPrice()) || !validateStockLevel(dto.getStock())) {
                productsToUpdate.remove(dto);
                invalidItems.add(dto);
            }
        }

        try {
            vinylDAO.update(productsToUpdate);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during update operation", e);
        }

        return invalidItems;
    }

    public void deleteService(List<VinylDTO> productsToDelete) {
        try {
            vinylDAO.delete(productsToDelete);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during delete operation", e);
        }
    }
}
