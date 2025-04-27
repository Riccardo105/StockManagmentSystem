package org.example.model.DAO.AccessControl;

import org.example.config.DbConnection;
import org.example.model.DAO.accessControl.RolePermissionDAO;
import org.example.model.DTO.AccessControl.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class RolePermissionsDAOIntegrationTest {
    private static SessionFactory sessionFactory;

    @BeforeAll
    public static void setUp() {
        System.setProperty("test.env", "true");
        sessionFactory = DbConnection.getSessionFactory();

        RoleDTO role = new RoleDTO("Test Role 1");
        RoleDTO role2 = new RoleDTO("Test Role 2");
        OperationDTO operation = new OperationDTO("Test operation 1");
        OperationDTO operation2 = new OperationDTO("Test operation 2");
        ResourceDTO resource = new ResourceDTO("Test resource 1");
        ResourceDTO resource2 = new ResourceDTO("Test resource 2");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(role);
        session.save(role2);
        session.save(operation);
        session.save(operation2);
        session.save(resource);
        session.save(resource2);

        PermissionsDTO permission = new PermissionsDTO(operation, resource);
        PermissionsDTO permission2 = new PermissionsDTO(operation2, resource2);
        RolePermissionsDTO rolePermission = new RolePermissionsDTO(role, permission);
        RolePermissionsDTO rolePermission2 = new RolePermissionsDTO(role2, permission2);

        session.save(permission);
        session.save(permission2);
        session.save(rolePermission);
        session.save(rolePermission2);

        session.getTransaction().commit();
        session.close();
    }

    @AfterAll
    public static void tearDown() {
        Session session = sessionFactory.openSession();
        String hql1 = "delete from RolePermissionsDTO";
        String hql2 = "delete from RoleDTO";
        String hql3 = "delete from PermissionsDTO";
        String hql4 = "delete from OperationDTO";
        String hql5 = "delete from ResourceDTO";
        session.beginTransaction();
        session.createQuery(hql1).executeUpdate();
        session.createQuery(hql2).executeUpdate();
        session.createQuery(hql3).executeUpdate();
        session.createQuery(hql4).executeUpdate();
        session.createQuery(hql5).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testGetPermissionsForOneRole(){
        Session session = sessionFactory.openSession();
        RoleDTO role = session.createQuery("FROM RoleDTO", RoleDTO.class)
                .setMaxResults(1)
                .uniqueResult();

        RolePermissionDAO rolePermissions = new RolePermissionDAO(sessionFactory);
        List<PermissionsDTO> permissions = rolePermissions.getPermissionsForRole(role);

        assertNotNull(permissions);
        assertEquals("Test operation 1", permissions.getFirst().getOperation().getName());
        assertEquals("Test resource 1", permissions.getFirst().getResource().getName());
    }

    @Test
    public void testGetPermissionsForMultipleRoles(){
        Session session = sessionFactory.openSession();
        List<RoleDTO> roles = session.createQuery("FROM RoleDTO", RoleDTO.class)
                                .getResultList();

        RolePermissionDAO rolePermissions = new RolePermissionDAO(sessionFactory);
        List<PermissionsDTO> permissions = rolePermissions.getPermissionsForRoles(roles);

        assertNotNull(permissions);

        assertEquals("Test operation 1", permissions.getFirst().getOperation().getName());
        assertEquals("Test resource 1", permissions.getFirst().getResource().getName());
        assertEquals("Test operation 2", permissions.getLast().getOperation().getName());
        assertEquals("Test resource 2", permissions.getLast().getResource().getName());
    }



}
