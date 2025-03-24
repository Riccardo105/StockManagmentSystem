package org.example.DAO.AccessControl;

import org.example.model.DTO.AccessControl.ResourceDTO;
import org.example.model.DTO.AccessControl.RoleDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

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
    public void readAll() {}
}
