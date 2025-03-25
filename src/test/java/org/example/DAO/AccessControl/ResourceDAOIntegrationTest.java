package org.example.DAO.AccessControl;

import org.example.DAO.AbstractDAOIntegrationTest;
import org.example.model.DAO.accessControl.ResourceDAO;
import org.example.model.DTO.AccessControl.ResourceDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceDAOIntegrationTest extends AbstractDAOIntegrationTest<ResourceDTO> {

    // session passed by tests
    protected Integer HelperCreateDTO(Session session) {
        ResourceDTO resourceDTO = new ResourceDTO("Test resource");

        Transaction tx = session.beginTransaction();
        Integer generatedId = (Integer) session.save(resourceDTO);
        tx.commit();

        return generatedId;
    }

    protected void HelperDeleteDTO( Session session, ResourceDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Test
    public void testCreate(){
        ResourceDAO resourceDAO = new ResourceDAO(sessionFactory);
        ResourceDTO resourceDTO = new ResourceDTO("Test resource");

        Integer generatedId = resourceDAO.create(resourceDTO);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        ResourceDTO retrievedDTO = session.get(ResourceDTO.class, generatedId);
        tx.commit();
        session.close();

        HelperDeleteDTO(sessionFactory.openSession(), retrievedDTO);
        assertNotNull(retrievedDTO);


    }
    @Test
    public void testRead() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        ResourceDAO resourceDAO = new ResourceDAO(sessionFactory);

        ResourceDTO resourceDTO = resourceDAO.read(generatedId);
        HelperDeleteDTO(sessionFactory.openSession(), resourceDTO);

        assertEquals("Test resource", resourceDTO.getName());

    }
    @Test
    public void testDelete() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        ResourceDAO resourceDAO = new ResourceDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        ResourceDTO resourceToDelete = session.get(ResourceDTO.class, generatedId);
        if (resourceToDelete != null) {
            resourceDAO.delete(resourceToDelete);
        }

        tx.commit();
        session.close();

        ResourceDTO deletedResource = resourceDAO.read(generatedId);
        assertNull(deletedResource);
    }
    @Test
    public void testDeleteList() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        Integer generatedId2 = HelperCreateDTO(sessionFactory.openSession());
        ResourceDAO resourceDAO = new ResourceDAO(sessionFactory);
        ArrayList<ResourceDTO> resourcesToDelete = new ArrayList<ResourceDTO>();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        resourcesToDelete.add(session.get(ResourceDTO.class, generatedId));
        resourcesToDelete.add(session.get(ResourceDTO.class, generatedId2));

        resourceDAO.delete(resourcesToDelete);

        tx.commit();
        session.close();

        ResourceDTO deletedResource = resourceDAO.read(generatedId);
        ResourceDTO deletedResource2 = resourceDAO.read(generatedId2);

        assertNull(deletedResource);
        assertNull(deletedResource2);
    }
    @Test
    public void testUpdate() {}
    @Test
    public void readAll() {
        HelperCreateDTO(sessionFactory.openSession());
        HelperCreateDTO(sessionFactory.openSession());

        ResourceDAO resourceDAO = new ResourceDAO(sessionFactory);

        List<ResourceDTO> operationsRead = resourceDAO.readAll();

        assertEquals(2, operationsRead.size());

    };
}
