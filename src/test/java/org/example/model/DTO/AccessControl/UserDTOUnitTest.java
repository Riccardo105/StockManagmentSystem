package org.example.model.DTO.AccessControl;

import org.example.model.DTO.AccessControl.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDTOUnitTest {

    @Test
    public void TestValidObjectCreation() {
        UserDTO userDTO = new UserDTO.Builder()
                .setFirstName("Test name")
                .setLastName("Test surname")
                .setEmail("Test email")
                .setPassword("Test password")
                .build();

        String expectedDTO = "UserDTO{firstName='Test name', lastName='Test surname', email='Test email', " +
                "password='Test password', activated='false'}";

        assertEquals(expectedDTO, userDTO.toString());
    }
}
