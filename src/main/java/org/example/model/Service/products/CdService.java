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
    CdDAO cdDAO;

    @Inject
    public CdService(CdDAO cdDAO) {
        this.cdDAO = cdDAO;
        this.requiredFields = Arrays.asList("title", "stock", "buyingPrice", "sellingPrice", "format", "artist",
                "genre","releaseDate", "playTime", "tracksNum", "numOfDiscs",
                "conditions", "bitrateMbps");
    }

    public void createService(Map<String, Object> formData) {
        validateObjectCreation(formData);
        CdDTO cdDTO = new CdDTO.Builder()
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
                .setNumOfDiscs((Integer) formData.get("numOfDiscs"))
                .setConditions(formData.get("conditions").toString())
                .setBitrateMbps((Integer) formData.get("bitrateMbps"))
                .build();

        try {
            cdDAO.create(cdDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error while creating audio book", e);
        }
    }


    /**
     *
     * @param productsToUpdate products the user as modified
     * @return list of invalid objects, so that the UI can identify the ones and show them to the user
     */
    public List<CdDTO> updateService(List<CdDTO> productsToUpdate) {
        List<CdDTO> invalidItems = new ArrayList<>();

        for (CdDTO dto: productsToUpdate){
            if (!validatePrice(dto.getSellingPrice(), dto.getBuyingPrice()) || !validateStockLevel(dto.getStock())) {
                productsToUpdate.remove(dto);
                invalidItems.add(dto);
            }
        }

        try {
            cdDAO.update(productsToUpdate);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during update operation", e);
        }

        return invalidItems;
    }

    public void deleteService(List<CdDTO> productsToDelete) {
        try {
            cdDAO.delete(productsToDelete);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error during delete operation", e);
        }
    }
}
