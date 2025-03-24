package org.example.DAO.AccessControl;

import org.example.model.DAO.accessControl.UserDAO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOIntegrationTest extends AccessControlAbstractIntegrationTest<UserDTO> {

    // session passed by tests
    protected Integer HelperCreateDTO(Session session) {
        UserDTO userDTO = new UserDTO.Builder()
                .setFirstName("Test first name")
                .setLastName("Test last name")
                .setUsername("Test username")
                .setPassword("Test Password")
                .setEmail("Test email")
                .build();

        Transaction tx = session.beginTransaction();
        Integer generatedId = (Integer) session.save(userDTO);
        tx.commit();

        return generatedId;
    }

    protected void HelperDeleteDTO( Session session, UserDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Test
    public void testCreate(){
        UserDAO userDAO = new UserDAO(sessionFactory);
        UserDTO resourceDTO = new UserDTO.Builder()
                .setFirstName("Test first name")
                .setLastName("Test last name")
                .setUsername("Test username")
                .setPassword("Test Password")
                .setEmail("Test email")
                .build();

        Integer generatedId = userDAO.create(resourceDTO);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        UserDTO retrievedDTO = session.get(UserDTO.class, generatedId);
        tx.commit();
        session.close();

        HelperDeleteDTO(sessionFactory.openSession(), retrievedDTO);
        assertNotNull(retrievedDTO);


    }
    @Test
    public void testRead() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        UserDAO userDAO = new UserDAO(sessionFactory);

        UserDTO userDTO = userDAO.read(generatedId);
        HelperDeleteDTO(sessionFactory.openSession(), userDTO);

        assertEquals("Test first name", userDTO.getFirstName());

    }
    @Test
    public void testDelete() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        UserDAO userDAO = new UserDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        UserDTO roleToDelete = session.get(UserDTO.class, generatedId);
        if (roleToDelete != null) {
            userDAO.delete(roleToDelete);
        }

        tx.commit();
        session.close();

        UserDTO deletedRole = userDAO.read(generatedId);
        assertNull(deletedRole);
    }
    @Test
    public void testDeleteList() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        Integer generatedId2 = HelperCreateDTO(sessionFactory.openSession());
        UserDAO userDAO = new UserDAO(sessionFactory);
        ArrayList<UserDTO> rolesToDelete = new ArrayList<UserDTO>();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        rolesToDelete.add(session.get(UserDTO.class, generatedId));
        rolesToDelete.add(session.get(UserDTO.class, generatedId2));

        userDAO.delete(rolesToDelete);

        tx.commit();
        session.close();

        UserDTO deletedUser = userDAO.read(generatedId);
        UserDTO deletedUser2 = userDAO.read(generatedId2);

        assertNull(deletedUser);
        assertNull(deletedUser2);
    }
    @Test
    public void testUpdate() {}
    @Test
    public void readAll() {
        HelperCreateDTO(sessionFactory.openSession());
        HelperCreateDTO(sessionFactory.openSession());

        UserDAO userDAO = new UserDAO(sessionFactory);

        List<UserDTO> operationsRead = userDAO.readAll();

        assertEquals(2, operationsRead.size());

    };
}
