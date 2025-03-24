package org.example.DTO.AccessControl;

import org.example.model.DTO.AccessControl.OperationDTO;
import org.example.model.DTO.AccessControl.PermissionsDTO;
import org.example.model.DTO.AccessControl.ResourceDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PermissionDTOUnitTest {

    @Test
    public void testValidObjectCreation() {
        ResourceDTO resourceDTO = new ResourceDTO("test resource");
        OperationDTO operationDTO = new OperationDTO("test operation");
        PermissionsDTO dto = new PermissionsDTO(operationDTO, resourceDTO);

        assertEquals(operationDTO, dto.getOperation());
        assertEquals(resourceDTO, dto.getResource());
    }
}
