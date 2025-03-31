package org.example.model.DAO.accessControl;


import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * UserRoleDAO does not follow standard AbstractDAO
 * due to difference in crud operations for join tables
 */
public class UserRoleDAO  {
    private final SessionFactory sessionFactory;

    public UserRoleDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public List<RoleDTO> getRolesForUser(UserDTO user) {
        try (Session session = sessionFactory.openSession()) {
            // First get direct roles
            List<RoleDTO> directRoles = session.createQuery(
                            "SELECT ur.role FROM UserRoleDTO ur WHERE ur.user.id = :userId",
                            RoleDTO.class)
                    .setParameter("userId", user.getId())
                    .getResultList();

            // If no direct roles, return empty list
            if (directRoles.isEmpty()) {
                return directRoles;
            }

            // Get all parent roles recursively using native SQL
            List<RoleDTO> inheritedRoles = session.createNativeQuery("""
            WITH RECURSIVE role_hierarchy AS (
                SELECT r.* FROM roles r
                WHERE r.id IN (:roleIds)
                UNION ALL SELECT r.* FROM roles r
                JOIN role_hierarchy rh ON r.id = rh.parent_role_id
                WHERE r.parent_role_id IS NOT NULL
            )
            SELECT * FROM role_hierarchy
            """, RoleDTO.class)
                    .setParameterList("roleIds", directRoles.stream().map(RoleDTO::getId).collect(Collectors.toList()))
                    .getResultList();

            // Combine and remove duplicates
            Set<RoleDTO> allRoles = new LinkedHashSet<>();
            allRoles.addAll(directRoles);
            allRoles.addAll(inheritedRoles);

            return new ArrayList<>(allRoles);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get roles for user ID: " + user.getId(), e);
        }
    }
}

