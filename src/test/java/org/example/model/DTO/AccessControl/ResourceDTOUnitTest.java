package org.example.model.DTO.AccessControl;

import org.example.model.DTO.AccessControl.ResourceDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceDTOUnitTest {

    @Test
    public void TestValidObjectCreation() {
        ResourceDTO resourceDTO = new ResourceDTO("product");

        String expectedDTO = "ResourceDTO{name='product'}";

        assertEquals(expectedDTO, resourceDTO.toString());
    }
}
