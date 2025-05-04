package org.example.model.Service.products;


import org.example.config.ObjectValidationException;
import org.example.model.DTO.products.ProductDTO;

import java.util.*;

/**
 * each product service takes in an instance of its corresponding DAO
 */
public abstract class AbstractProductsService<T extends ProductDTO> {

    // each concrete service populate this list in the constructor
    List<String> requiredFields = new ArrayList<>();
    Map<String, String> errorMap = new HashMap<>();

    protected void validatePrice(float sellingPrice , float buyingPrice){
        if (sellingPrice < (buyingPrice * 1.5)){
            errorMap.put("sellingPrice", "Selling Price must at least 1.5 times the buying cost");
        }
    }
    protected void validateStockLevel(int stockLevel){
        if (stockLevel < 0){
            errorMap.put("stockLevel", "Stock Level must not be negative");
        };
    }

    public void validateObjectFields(Map<String, ?> formData) {

        for (String field : requiredFields) {
            if (formData.get(field) == null) {
                errorMap.put(field, "Field must not be null");
            }
        }
    }

    /** all checks grouped here to be called by concrete classes
     * this method throws a custom ObjectCreationException populated with an errors map
     * @param formData the data from the UI
     */
    public void validateObjectCreation(Map<String, Object> formData) {
        errorMap.clear();

         validateObjectFields(formData);

        if (!errorMap.isEmpty()) {
            throw new ObjectValidationException(errorMap);
        }

         // UI has taken care of parsing inputs before populating formData
         validatePrice((float) formData.get("sellingPrice"), (float) formData.get("buyingPrice"));
         validateStockLevel((Integer) formData.get("stock"));
        if (!errorMap.isEmpty()) {
            throw new ObjectValidationException(errorMap);
        }
    }

    public abstract T createService(Map<String,Object> params);
    public void updateService(ProductDTO dto){
         errorMap.clear();
         validateStockLevel( (Integer) dto.getStock());
         validatePrice(dto.getSellingPrice(), dto.getBuyingPrice());

         if (!errorMap.isEmpty()) {
             throw new ObjectValidationException(errorMap);
         }
     };


}
