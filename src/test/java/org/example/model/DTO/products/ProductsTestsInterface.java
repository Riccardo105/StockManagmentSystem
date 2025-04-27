package org.example.model.DTO.products;

/**
 * testing interface for product DTOs
 */
public interface ProductsTestsInterface<T> {

    /**
     * method used to create a utility for the test scripts
     * @return an object of the type of the implementing class
     */
    T createTestProductUtility();
    /**
     * a complete and valid object is created to test the correct functioning of the builder
     */
    void testValidObjectCreation();

    /**
     * functioning of  method is under test here
     * no business logic is handled at this stage
     */
    void testUpdateStock();

    /**
     * functioning of  method is under test here
     * no business logic is handled at this stage
     */
    void testUpdateBuyingPrice();

    /**
     * functioning of method is under test here
     * no business logic is handled at this stage
     */
    void testUpdateSellingPrice();

}
