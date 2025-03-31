package org.example.DTO.AccessControl;

import org.example.model.DTO.AccessControl.RolePermissionsId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class
RolePermissionIdUnitTest {

    @Test
    public void testValidObjectCreation() {
        RolePermissionsId rolePermissionsId = new RolePermissionsId(1, 2);

        assertEquals(1, rolePermissionsId.getRoleId());
        assertEquals(2, rolePermissionsId.getPermissionId());
    }
}
