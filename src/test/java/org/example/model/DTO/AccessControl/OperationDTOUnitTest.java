package org.example.model.DTO.AccessControl;

import org.example.model.DTO.AccessControl.OperationDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationDTOUnitTest {

    @Test
    public void testValidObjectCreation() {
        OperationDTO operationDTO = new OperationDTO("Create");

        String expectedDTO = "OperationDTO{name='Create'}";

        assertEquals(expectedDTO, operationDTO.toString());
    }
}
