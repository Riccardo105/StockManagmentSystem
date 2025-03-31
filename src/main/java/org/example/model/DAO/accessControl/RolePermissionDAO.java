package org.example.model.DAO.accessControl;
import org.example.model.DTO.AccessControl.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<PermissionsDTO> getPermissionsForRole(List<RoleDTO> roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }

        final String hql = """
        SELECT p FROM RolePermissionsDTO rp
        JOIN FETCH rp.permission p
        JOIN FETCH p.operation
        JOIN FETCH p.resource
        WHERE rp.role.id IN (:roleIds)
        """;

        List<Integer> roleIds = roles.stream()
                .map(RoleDTO::getId)
                .collect(Collectors.toList());

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(hql, PermissionsDTO.class)
                    .setParameterList("roleIds", roleIds)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch permissions for roles", e);
        }
    }
}
