package org.example.DAO.AccessControl;

import org.example.model.DAO.accessControl.RoleDAO;
import org.example.model.DTO.AccessControl.RoleDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoleDAOIntegrationTest extends AccessControlAbstractIntegrationTest<RoleDTO> {

    // session passed by tests
    protected Integer HelperCreateDTO(Session session) {
        RoleDTO roleDTO = new RoleDTO("Test role");

        Transaction tx = session.beginTransaction();
        Integer generatedId = (Integer) session.save(roleDTO);
        tx.commit();

        return generatedId;
    }

    protected void HelperDeleteDTO( Session session, RoleDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Test
    public void testCreate(){
        RoleDAO roleDAO = new RoleDAO(sessionFactory);
        RoleDTO resourceDTO = new RoleDTO("Test resource");

        Integer generatedId = roleDAO.create(resourceDTO);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        RoleDTO retrievedDTO = session.get(RoleDTO.class, generatedId);
        tx.commit();
        session.close();

        HelperDeleteDTO(sessionFactory.openSession(), retrievedDTO);
        assertNotNull(retrievedDTO);


    }
    @Test
    public void testRead() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        RoleDAO roleDAO = new RoleDAO(sessionFactory);

        RoleDTO roleDTO = roleDAO.read(generatedId);
        HelperDeleteDTO(sessionFactory.openSession(), roleDTO);

        assertEquals("Test role", roleDTO.getName());

    }
    @Test
    public void testDelete() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        RoleDAO roleDAO = new RoleDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        RoleDTO roleToDelete = session.get(RoleDTO.class, generatedId);
        if (roleToDelete != null) {
            roleDAO.delete(roleToDelete);
        }

        tx.commit();
        session.close();

        RoleDTO deletedRole = roleDAO.read(generatedId);
        assertNull(deletedRole);
    }
    @Test
    public void testDeleteList() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        Integer generatedId2 = HelperCreateDTO(sessionFactory.openSession());
        RoleDAO roleDAO = new RoleDAO(sessionFactory);
        ArrayList<RoleDTO> rolesToDelete = new ArrayList<RoleDTO>();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        rolesToDelete.add(session.get(RoleDTO.class, generatedId));
        rolesToDelete.add(session.get(RoleDTO.class, generatedId2));

        roleDAO.delete(rolesToDelete);

        tx.commit();
        session.close();

        RoleDTO deletedResource = roleDAO.read(generatedId);
        RoleDTO deletedResource2 = roleDAO.read(generatedId2);

        assertNull(deletedResource);
        assertNull(deletedResource2);
    }
    @Test
    public void testUpdate() {}
    @Test
    public void readAll() {
        HelperCreateDTO(sessionFactory.openSession());
        HelperCreateDTO(sessionFactory.openSession());

        RoleDAO roleDAO = new RoleDAO(sessionFactory);

        List<RoleDTO> operationsRead = roleDAO.readAll();

        assertEquals(2, operationsRead.size());

    };
}
