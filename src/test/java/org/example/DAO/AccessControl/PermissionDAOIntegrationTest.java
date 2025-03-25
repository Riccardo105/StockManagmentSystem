package org.example.DAO.AccessControl;


import org.example.model.DAO.accessControl.PermissionsDAO;
import org.example.model.DTO.AccessControl.OperationDTO;
import org.example.model.DTO.AccessControl.PermissionsDTO;
import org.example.model.DTO.AccessControl.ResourceDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PermissionDAOIntegrationTest extends AccessControlAbstractIntegrationTest<PermissionsDTO> {

    // session passed by tests
    protected Integer HelperCreateDTO(Session session) {
        ResourceDTO resource = new ResourceDTO("test resource");
        OperationDTO operation = new OperationDTO("test operation");
        PermissionsDTO permission = new PermissionsDTO(operation, resource);

        Transaction tx = session.beginTransaction();
        session.save(resource);
        session.save(operation);
        Integer id = (Integer) session.save(permission);

        tx.commit();
        session.close();
        return id;
    }

    protected void HelperDeleteDTO( Session session, PermissionsDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        session.delete(dto.getOperation());
        session.delete(dto.getResource());
        tx.commit();
        session.close();
    }

    @Test
    public void testCreate(){
        ResourceDTO resourceDTO = new ResourceDTO("test resource");
        OperationDTO operationDTO = new OperationDTO("test operation");

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();
        session.save(resourceDTO);
        session.save(operationDTO);
        tx.commit();

        session.refresh(resourceDTO);
        session.refresh(operationDTO);

        PermissionsDTO permissionDTO = new PermissionsDTO(operationDTO, resourceDTO);
        session.close();

        PermissionsDAO permissionsDAO = new PermissionsDAO(sessionFactory);

        Integer generatedId = permissionsDAO.create(permissionDTO);
        System.out.println("Generated ID: " + generatedId);

        Session session2 = sessionFactory.openSession();
        Transaction tx2 = session2.beginTransaction();
        PermissionsDTO permission = session2.get(PermissionsDTO.class, generatedId);
        tx2.commit();
        session2.close();

        HelperDeleteDTO(sessionFactory.openSession(), permissionDTO);
        assertNotNull(permission);
    }

    @Test
    public void testRead() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        PermissionsDAO permissionsDAO = new PermissionsDAO(sessionFactory);

        PermissionsDTO permissionDTO = permissionsDAO.read(generatedId);
        HelperDeleteDTO(sessionFactory.openSession(), permissionDTO);

        assertNotNull(permissionDTO);
    }
    @Test
    public void testDelete() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        PermissionsDAO permissionsDAO = new PermissionsDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        PermissionsDTO permissionToDelete = session.get(PermissionsDTO.class, generatedId);
        if (permissionToDelete != null) {
            permissionsDAO.delete(permissionToDelete);
        }

        session.delete(permissionToDelete.getOperation());
        session.delete(permissionToDelete.getResource());

        tx.commit();
        session.close();

        PermissionsDTO deletedPermission = permissionsDAO.read(generatedId);
        assertNull(deletedPermission);


    }
    @Test
    public void testDeleteList() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        Integer generatedId2 = HelperCreateDTO(sessionFactory.openSession());
        PermissionsDAO permissionsDAO = new PermissionsDAO(sessionFactory);
        ArrayList<PermissionsDTO> permissionsToDelete = new ArrayList<>();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        permissionsToDelete.add(session.get(PermissionsDTO.class, generatedId));
        permissionsToDelete.add(session.get(PermissionsDTO.class, generatedId2));

        permissionsDAO.delete(permissionsToDelete);


        session.delete(permissionsToDelete.getFirst().getOperation());
        session.delete(permissionsToDelete.getFirst().getResource());
        session.delete(permissionsToDelete.get(1).getOperation());
        session.delete(permissionsToDelete.get(1).getResource());

        tx.commit();
        session.close();

        PermissionsDTO deletedPermission = permissionsDAO.read(generatedId);
        PermissionsDTO deletedPermission2 = permissionsDAO.read(generatedId2);

        assertNull(deletedPermission);
        assertNull(deletedPermission2);
    }
    @Test
    public void testUpdate() {}
    @Test
    public void readAll() {
        HelperCreateDTO(sessionFactory.openSession());
        HelperCreateDTO(sessionFactory.openSession());

        PermissionsDAO permissionsDAO = new PermissionsDAO(sessionFactory);
        List<PermissionsDTO> operationsRead = permissionsDAO.readAll();

        HelperDeleteDTO(sessionFactory.openSession(), operationsRead.get(0));
        HelperDeleteDTO(sessionFactory.openSession(), operationsRead.get(1));

        assertEquals(2, operationsRead.size());

    }
}
