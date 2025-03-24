package org.example.DAO.AccessControl;

import org.example.DAO.AbstractDAOIntegrationTest;
import org.example.model.DTO.AccessControl.OperationDTO;
import org.example.model.DTO.AccessControl.ResourceDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

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
    public void testCreate(){}
    @Test
    public void testRead() {}
    @Test
    public void testDelete() {}
    @Test
    public void testDeleteList() {}
    @Test
    public void testUpdate() {}
    @Test
    public void readAll() {};
}
