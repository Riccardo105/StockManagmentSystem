package org.example.DAO.AccessControl;


import org.example.config.DbConnection;
import org.example.model.DAO.accessControl.UserRoleDAO;
import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.example.model.DTO.AccessControl.UserRoleDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.* ;
import java.util.List;

public class UserRoleDAOIntegrationTest   {
    private static SessionFactory sessionFactory;
    @BeforeAll
    public static void setUp(){
        System.setProperty("test.env", "true");
        sessionFactory = DbConnection.getSessionFactory();

        RoleDTO role1 = new RoleDTO("1stRole");
        RoleDTO role2 = new RoleDTO("2ndRole", role1);
        RoleDTO role3 = new RoleDTO("3rdRole", role2);
        UserDTO user = new UserDTO.Builder()
                .setFirstName("Test name")
                .setLastName("Test surname")
                .setUsername("Test username")
                .setEmail("Test email")
                .setPassword("Test password")
                .build();

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(role1);
        session.save(role2);
        session.save(role3);
        session.save(user);

        UserRoleDTO userRole = new UserRoleDTO(user, role3);
        session.save(userRole);
        session.getTransaction().commit();
        session.close();

    }

    @AfterAll
    public static void tearDown(){
        Session session = sessionFactory.openSession();
        String hql1 = "delete from UserRoleDTO";
        String hql3 = "delete from UserDTO";
        session.beginTransaction();
        session.createQuery(hql1).executeUpdate();
        session.createQuery(hql3).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }


    @Test
    public void testGetRolesForUser(){
        UserRoleDAO userRoleDAO = new UserRoleDAO(sessionFactory);
        Session session = sessionFactory.openSession();
        UserDTO user = session.createQuery("FROM UserDTO", UserDTO.class)
                .setMaxResults(1)
                .uniqueResult();

        List<RoleDTO> results = userRoleDAO.getRolesForUser(user);

        for (RoleDTO role : results) {
            session.delete(role);
        }

        assertEquals(3, results.size());
        assertEquals("3rdRole", results.getFirst().getName());
        assertEquals("2ndRole", results.get(1).getName());
        assertEquals("1stRole", results.getLast().getName());



    }


}
