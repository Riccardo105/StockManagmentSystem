package org.example.DTO.user;

import org.example.model.DTO.user.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDTOUnitTest {

    @Test
    public void TestValidObjectCreation() {
        UserDTO userDTO = new UserDTO.Builder()
                .setFirstName("Test name")
                .setLastName("Test surname")
                .setUsername("Test username")
                .setEmail("Test email")
                .setPassword("Test password")
                .build();

        String expectedDTO = "UserDTO{firstName='Test name', lastName='Test surname', username='Test username', email='Test email', " +
                "password='Test password', activated='false'}";

        assertEquals(expectedDTO, userDTO.toString());
    }
}
