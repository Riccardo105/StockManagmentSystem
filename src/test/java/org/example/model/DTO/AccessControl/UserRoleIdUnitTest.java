package org.example.model.DTO.AccessControl;

import org.example.model.DTO.AccessControl.UserRoleId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserRoleIdUnitTest {

    @Test
    public void TestValidObjectCreation() {
        UserRoleId userRoleId = new UserRoleId(1, 2);

        assertEquals(1, userRoleId.getUserId());
        assertEquals(2, userRoleId.getRoleId());
    }

}
