package org.example.model.DAO.accessControl;
import org.example.model.DTO.AccessControl.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * RolePermissionDAO does not follow standard AbstractDAO
 * due to difference in crud operations for join tables
 */
public class RolePermissionDAO {
    private final SessionFactory sessionFactory;

    public RolePermissionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<PermissionsDTO> getPermissionsForRole(RoleDTO role) {
        final String hql = """
        SELECT p FROM RolePermissionsDTO rp
        JOIN FETCH rp.permission p
        JOIN FETCH p.operation
        JOIN FETCH p.resource
        WHERE rp.role.id = :roleId
        """;

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(hql, PermissionsDTO.class)
                    .setParameter("roleId", role.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch permissions for role ID: " + role.getId(), e);
        }
    }
}
