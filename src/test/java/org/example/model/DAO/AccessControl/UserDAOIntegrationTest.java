package org.example.model.DAO.AccessControl;

import org.example.config.DbConnection;
import org.example.model.DAO.accessControl.UserDAO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.* ;


public class UserDAOIntegrationTest {
    protected static SessionFactory sessionFactory ;

    @BeforeAll
    public static void setUpSessionFactory() {
        System.setProperty("test.env", "true");
        sessionFactory = DbConnection.getSessionFactory();
        System.out.println("SessionFactoryCreated");
    }

    private Integer HelperCreate(){
        UserDTO userDTO = new UserDTO.Builder()
                .setFirstName("Test name")
                .setLastName("Test surname")
                .setEmail("Test email")
                .setPassword("Test password")
                .build();

        Session userSession = sessionFactory.openSession();
        userSession.beginTransaction();
        Integer generatedId = (Integer) userSession.save(userDTO);
        userSession.getTransaction().commit();
        userSession.close();
        return generatedId;

    }
    private void HelperDelete(UserDTO dto){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(dto);
        session.getTransaction().commit();
        session.close();
    }


    @Test
    public void testCreate() {
        UserDTO userDTO = new UserDTO.Builder()
                .setFirstName("Test name")
                .setLastName("Test surname")
                .setEmail("Test email")
                .setPassword("Test password")
                .build();

        UserDAO userDAO = new UserDAO(sessionFactory);
        Integer generatedId = (Integer) userDAO.create(userDTO);

        Session session = sessionFactory.openSession();
        UserDTO retrievedUserDTO = session.get(UserDTO.class, generatedId);
        session.delete(retrievedUserDTO);
        session.close();

        assertEquals(userDTO.toString(), retrievedUserDTO.toString());
    }

    @Test
    public void testGetEmail() {
        HelperCreate();

        UserDAO userDAO = new UserDAO(sessionFactory);
        UserDTO retrievedUserDTO = userDAO.getByEmail("Test email");

        HelperDelete(retrievedUserDTO);

        assertNotNull(retrievedUserDTO);



    }
    @Test
    public void testGetNotActivatedAccounts() {
        HelperCreate();
        HelperCreate();
        UserDAO userDAO = new UserDAO(sessionFactory);

        List<UserDTO> retrievedDTOs = userDAO.getNoneActivatedAccounts();

        for (UserDTO dto : retrievedDTOs) {
            HelperDelete(dto);
        }

        assertEquals(2, retrievedDTOs.size());
    }
    @Test
    public void testActivateAccount() {
        Integer generatedId = HelperCreate();
        UserDAO userDAO = new UserDAO(sessionFactory);
        Session session = sessionFactory.openSession();
        UserDTO dto =  session.get(UserDTO.class, generatedId);
        userDAO.activateAccount(dto);
        session.refresh(dto);
        session.delete(dto);

        assertTrue(dto.isActivated());
    }
}
