package org.example.model.service.AccessControl;

import org.example.config.DbConnection;
import org.example.model.DAO.accessControl.RolePermissionDAO;
import org.example.model.DAO.accessControl.UserDAO;
import org.example.model.DAO.accessControl.UserRoleDAO;
import org.example.model.DTO.AccessControl.*;
import org.example.model.Service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.* ;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSessionIntegrationTest {
    protected static SessionFactory sessionFactory ;
    protected UserDAO userDAO = new UserDAO(sessionFactory);
    protected UserRoleDAO userRoleDAO = new UserRoleDAO(sessionFactory);
    protected RolePermissionDAO rolePermissionDAO = new RolePermissionDAO(sessionFactory);
    protected BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeAll
    public static void setUp() {
        System.setProperty("test.env", "true");
        sessionFactory = DbConnection.getSessionFactory();

        OperationDTO operation = new OperationDTO("Test Operation");
        ResourceDTO resource = new ResourceDTO("Test Resource");
        RoleDTO role = new RoleDTO("Sales Assistant");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(operation);
        session.save(resource);
        session.save(role);

        PermissionDTO permission = new PermissionDTO(operation, resource);
        session.save(permission);
        session.getTransaction().commit();
        session.close();

    }

    @AfterAll
    public static void tearDown(){
        Session session = sessionFactory.openSession();
        String hql1 = "delete from PermissionsDTO";
        String hql2 = "delete from OperationDTO";
        String hql3 = "delete from ResourceDTO";
        String hql4 = "delete from UserRoleDTO";
        String hql5 = "delete from RoleDTO";
        String hql6 = "delete from UserDTO";
        session.beginTransaction();
        session.createQuery(hql1).executeUpdate();
        session.createQuery(hql2).executeUpdate();
        session.createQuery(hql3).executeUpdate();
        session.createQuery(hql4).executeUpdate();
        session.createQuery(hql5).executeUpdate();
        session.createQuery(hql6).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }


    @Test
    public void testSetUserPermissions() {
        Session session = sessionFactory.openSession();
        List<PermissionDTO> permissions = session.createQuery("from PermissionsDTO", PermissionDTO.class).getResultList();
        UserService userService = new UserService(userDAO, userRoleDAO, rolePermissionDAO, encoder);
        userService.setUserPermissions(permissions);

        assertEquals(1, userService.getUserPermissions().size());

        userService.getUserPermissions().clear();

    }
    @Test
    public void testCheckUserHasPermission() {
        Session session = sessionFactory.openSession();
        List<PermissionDTO> permissions = session.createQuery("from PermissionsDTO", PermissionDTO.class).getResultList();
        UserService userService = new UserService(userDAO, userRoleDAO, rolePermissionDAO, encoder);
        userService.setUserPermissions(permissions);

        OperationDTO operation = new OperationDTO("Test Operation");
        ResourceDTO resource = new ResourceDTO("Test Resource");
        PermissionDTO permission = new PermissionDTO(operation, resource);

        assertTrue(userService.checkUserHasPermission(permission));
    }
    @Test
    public void testTearDownSession() {
        Session session = sessionFactory.openSession();
        List<PermissionDTO> permissions = session.createQuery("from PermissionsDTO", PermissionDTO.class).getResultList();
        UserService userService = new UserService(userDAO, userRoleDAO, rolePermissionDAO, encoder);
        userService.setUserPermissions(permissions);

        userService.clearUserSession();

        assertEquals(0, userService.getUserPermissions().size());
    }

    @Test
    public void testSignup(){

        UserService userService = new UserService(userDAO, userRoleDAO, rolePermissionDAO, encoder);

        // correct form
        Map<String, String> TestCase1 = new HashMap<>();
        TestCase1.put("firstName", "John");
        TestCase1.put("lastName", "Doe");
        TestCase1.put("email", "john.doe@example.com");
        TestCase1.put("password", "ValidPassword123@");

        // missing one field
        Map<String, String> TestCase2 = new HashMap<>();
        TestCase2.put("firstName", "John");
        TestCase2.put("lastName", null);
        TestCase2.put("email", "john.doe@example.com");
        TestCase2.put("password", "ValidPassword123@");

        // incorrect password
        Map<String, String> TestCase3 = new HashMap<>();
        TestCase3.put("firstName", "John");
        TestCase3.put("lastName", "Doe");
        TestCase3.put("email", "john.doe@example.com");
        TestCase3.put("password", "ValidPassword123");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.signUpHandler(TestCase2);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            userService.signUpHandler(TestCase3);
        });

        userService.signUpHandler(TestCase1);
        UserDTO createdUser = userDAO.getByEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", createdUser.getEmail());

        List<RoleDTO> role = userRoleDAO.getRolesForUser(createdUser);
        assertEquals("Sales Assistant", role.getFirst().getName());




    }
}
