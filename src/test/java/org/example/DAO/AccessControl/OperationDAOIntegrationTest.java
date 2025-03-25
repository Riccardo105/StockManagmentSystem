package org.example.DAO.AccessControl;

import org.example.model.DAO.accessControl.OperationDAO;
import org.example.model.DTO.AccessControl.OperationDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OperationDAOIntegrationTest extends AccessControlAbstractIntegrationTest<OperationDTO> {

    // session passed by tests
    protected Integer HelperCreateDTO(Session session) {
       OperationDTO operationDTO = new OperationDTO("Test operation");

        Transaction tx = session.beginTransaction();
        Integer generatedId = (Integer) session.save(operationDTO);
        tx.commit();
        session.close();

        return generatedId;
    }

    protected void HelperDeleteDTO( Session session, OperationDTO dto) {
        Transaction tx = session.beginTransaction();
        session.delete(dto);
        tx.commit();
        session.close();
    }

    @Test
    public void testCreate(){
        OperationDAO operationDAO = new OperationDAO(sessionFactory);
        OperationDTO operationDTO = new OperationDTO("Test operation");

        Integer generatedId = operationDAO.create(operationDTO);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        OperationDTO operation = session.get(OperationDTO.class, generatedId);
        tx.commit();
        session.close();

        HelperDeleteDTO(sessionFactory.openSession(), operation);


        assertNotNull(operation);
    }

    @Test
    public void testRead() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        OperationDAO operationDAO = new OperationDAO(sessionFactory);

        OperationDTO operationDTO = operationDAO.read(generatedId);
        HelperDeleteDTO(sessionFactory.openSession(), operationDTO);

        assertEquals("Test operation", operationDTO.getName());


    }
    @Test
    public void testDelete() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        OperationDAO operationDAO = new OperationDAO(sessionFactory);

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        OperationDTO operationToDelete = session.get(OperationDTO.class, generatedId);
        if (operationToDelete != null) {
            operationDAO.delete(operationToDelete);
        }

        tx.commit();
        session.close();

        OperationDTO deletedOperation = operationDAO.read(generatedId);
        assertNull(deletedOperation);


    }
    @Test
    public void testDeleteList() {
        Integer generatedId = HelperCreateDTO(sessionFactory.openSession());
        Integer generatedId2 = HelperCreateDTO(sessionFactory.openSession());
        OperationDAO operationDAO = new OperationDAO(sessionFactory);
        ArrayList<OperationDTO> operationsToDelete = new ArrayList<OperationDTO>();

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        operationsToDelete.add(session.get(OperationDTO.class, generatedId));
        operationsToDelete.add(session.get(OperationDTO.class, generatedId2));

        operationDAO.delete(operationsToDelete);

        tx.commit();
        session.close();

        OperationDTO deletedOperation = operationDAO.read(generatedId);
        OperationDTO deletedOperation2 = operationDAO.read(generatedId2);

        assertNull(deletedOperation);
        assertNull(deletedOperation2);
    }
    @Test
    public void testUpdate() {}
    @Test
    public void readAll() {
        HelperCreateDTO(sessionFactory.openSession());
        HelperCreateDTO(sessionFactory.openSession());

        OperationDAO operationDAO = new OperationDAO(sessionFactory);

        List<OperationDTO> operationsRead = operationDAO.readAll();

        for (OperationDTO operationDTO : operationsRead) {
            System.out.println(operationDTO.getName());
        }
        assertEquals(2, operationsRead.size());

    }
}
