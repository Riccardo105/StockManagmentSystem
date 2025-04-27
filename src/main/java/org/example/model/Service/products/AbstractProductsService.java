package org.example.model.Service.products;


import org.example.model.DTO.products.ProductDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * each product service takes in an instance of its corresponding DAO
 */
public abstract class AbstractProductsService<T extends ProductDTO> {

    // each concrete service populate this list in the constructor
    List<String> requiredFields;

    protected boolean validatePrice(float sellingPrice , float buyingCost){
        return  sellingPrice > ( buyingCost * 1.5);
    }
    protected boolean validateStockLevel(int stockLevel){
        return stockLevel > 0;
    }

    public boolean validateObjectFields(Map<String, ?> formData) {

        for (String field : requiredFields) {
            if (formData.get(field) == null) {
                return false;
            }
        }
        return true;
    }

    /** all checks grouped here to be called by concrete classes
     *
     * @param formData the data from the UI
     */
    public void validateObjectCreation(Map<String, Object> formData) {
        boolean isObjectValid = validateObjectFields(formData);
        boolean isPriceValid = validatePrice((float) formData.get("sellingPrice"), (float) formData.get("buyingPrice"));
        boolean isStockLevelValid = validateStockLevel((Integer) formData.get("stock"));
        if (!isObjectValid || !isPriceValid || !isStockLevelValid) {
            throw new IllegalArgumentException("invalid data");
        }
    }

    public abstract void createService(Map<String,Object> params);
    public abstract List<T> updateService(List<T> productsToUpdate);
    public abstract void deleteService(List<T> productsToDelete);

}
