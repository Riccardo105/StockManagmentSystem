package org.example.DTO.AccessControl;

import org.example.model.DTO.AccessControl.RoleDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RoleDTOUnitTest {
    @Test
    public void TestValidObjectCreation() {
        RoleDTO roleDTO = new RoleDTO("test Role");

        assertEquals( "test role", roleDTO.getName());
    }

    @Test
    public void TestValidObjectCreationWithParent() {
        RoleDTO ParentRoleDTO = new RoleDTO("test parent role");

        RoleDTO roleDTO = new RoleDTO("test Role", ParentRoleDTO);

        assertEquals( "test role", roleDTO.getName());
        assertEquals("test parent role", ParentRoleDTO.getName());
    }
}
