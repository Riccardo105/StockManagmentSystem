package org.example.DAO.AccessControl;

import org.example.DAO.AbstractDAOIntegrationTest;
import org.example.model.DAO.accessControl.UserDAO;
import org.example.model.DAO.accessControl.UserRoleDAO;
import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.example.model.DTO.AccessControl.UserRoleDTO;
import org.example.model.DTO.AccessControl.UserRoleId;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRoleDAOIntegrationTest extends AbstractDAOIntegrationTest<UserRoleDTO> {

    public Integer HelperCreateDTO(Session session){
        RoleDTO roleDTO = new RoleDTO("Test Role");
        UserDTO userDTO = new UserDTO.Builder()
                 .setFirstName("Test first name")
                 .setLastName("Test last name")
                 .setUsername("Test username")
                 .setPassword("Test Password")
                 .setEmail("Test email")
                 .build();

        Transaction tx = session.beginTransaction();
        Integer roleId = (Integer) session.save(roleDTO);
        Integer userId = (Integer) session.save(userDTO);
        tx.commit();

        UserRoleDTO userRoleDTO = new UserRoleDTO(userDTO, roleDTO);

        Transaction tx2 = session.beginTransaction();
        Integer userRoleId = (Integer) session.save(userRoleDTO);
        tx2.commit();
        session.close();

        return userRoleId;
    }

    public void HelperDeleteDTO(Session session, UserRoleDTO dto){
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }


    @Test
    public void testCreate(){
        RoleDTO roleDTO = new RoleDTO("Test Role");
        UserDTO userDTO = new UserDTO.Builder()
                .setFirstName("Test first name")
                .setLastName("Test last name")
                .setUsername("Test username")
                .setPassword("Test Password")
                .setEmail("Test email")
                .build();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Integer roleId = (Integer) session.save(roleDTO);
        Integer userId = (Integer) session.save(userDTO);
        tx.commit();
        session.close();

        UserRoleDTO userRoleDTO = new UserRoleDTO(userDTO, roleDTO);
        UserRoleDAO userRoleDAO = new UserRoleDAO(sessionFactory);

        userRoleDAO.create(userRoleDTO);

        UserRoleId userRoleId = new UserRoleId(userId, roleId);

        // Retrieve using the composite key
        Session session2 = sessionFactory.openSession();
        Transaction tx2 = session2.beginTransaction();
        UserRoleDTO retrievedDTO = session2.get(UserRoleDTO.class, userRoleId);
        tx2.commit();
        session2.close();

        // Verify
        assertNotNull(retrievedDTO);
        assertEquals(userId, retrievedDTO.getUser().getId());
        assertEquals(roleId, retrievedDTO.getRole().getId());

    }

    @Test
    public void testRead() {
    }
    @Test
    public void testDelete() {
    }
    @Test
    public void testDeleteList() {
    }
    @Test
    public void testUpdate() {}

    @Test
    public void readAll() {
    };

}
