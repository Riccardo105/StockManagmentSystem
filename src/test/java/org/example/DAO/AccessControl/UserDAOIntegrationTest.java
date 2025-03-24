package org.example.DAO.AccessControl;

import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

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
