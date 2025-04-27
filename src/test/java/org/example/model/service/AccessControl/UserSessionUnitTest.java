package org.example.model.service.AccessControl;

import org.example.config.DbConnection;
import org.example.model.DAO.accessControl.RolePermissionDAO;
import org.example.model.DAO.accessControl.UserDAO;
import org.example.model.DAO.accessControl.UserRoleDAO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.example.model.Service.UserSessionService;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserSessionUnitTest extends UserSessionService {

    public UserSessionUnitTest(UserDAO userDAO, UserRoleDAO userRoleDAO, RolePermissionDAO rolePermissionDAO) {
        super(userDAO, userRoleDAO, rolePermissionDAO);
    }

    @Test
    public void TestPasswordValidation() {
        String tooShort = "Ab1@";
        String noUppercase = "password1@";
        String noLowercase = "PASSWORD1@";
        String noNumbers = "Password@";
        String noSpecialChars = "Password1";
        String twoErrors = "Password";

        IllegalArgumentException ex1 = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidationHelper(tooShort)
        );
        IllegalArgumentException ex2 = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidationHelper(noUppercase)
        );
        IllegalArgumentException ex3 = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidationHelper(noLowercase)
        );IllegalArgumentException ex4 = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidationHelper(noNumbers)
        );IllegalArgumentException ex5 = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidationHelper(noSpecialChars)
        );IllegalArgumentException ex6 = assertThrows(
                IllegalArgumentException.class,
                () -> passwordValidationHelper(twoErrors)
        );


        assertEquals("Password failed validation: Minimum length 8 characters", ex1.getMessage());
        assertEquals("Password failed validation: at least one uppercase character", ex2.getMessage());
        assertEquals("Password failed validation: at least one lowercase character", ex3.getMessage());
        assertEquals("Password failed validation: at least one digit", ex4.getMessage());
        assertEquals("Password failed validation: at least one special character symbol", ex5.getMessage());
        assertEquals("Password failed validation: at least one digit, at least one special character symbol", ex6.getMessage());


    }
    private void createUserHelper(){
        System.setProperty("test.env", "true");

        UserDTO userDTO = new UserDTO.Builder()
                .setFirstName("Test name")
                .setLastName("Test surname")
                .setEmail("Test email")
                .setPassword("Test password")
                .build();

        Session session = DbConnection.getSession();
        session.beginTransaction();
        session.save(userDTO);
        session.getTransaction().commit();
        session.close();
    }

    private void deleteUserHelper(){
        Session session = DbConnection.getSession();
        session.beginTransaction();
        String hql = "delete from UserDTO";
        session.createQuery(hql).executeUpdate();
    }

    @Test
    public void TestEmailValidation() {
        createUserHelper();

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> emailValidationHelper("Test email")
        );

        deleteUserHelper();

    }
}
