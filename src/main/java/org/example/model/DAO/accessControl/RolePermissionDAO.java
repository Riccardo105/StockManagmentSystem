package org.example.model.DAO.accessControl;
import org.example.model.DTO.AccessControl.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.google.inject.Inject;

public class RolePermissionDAO {
    private final SessionFactory sessionFactory;

    @Inject
    public RolePermissionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<PermissionDTO> getPermissionsForRole(RoleDTO role) {
        if (role == null) {
            return Collections.emptyList();
        }

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT rp.permission FROM RolePermissionsDTO rp WHERE rp.role.id = :roleId",
                            PermissionDTO.class)
                    .setParameter("roleId", role.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch permissions for role ID: " + role.getId(), e);
        }
    }

    public List<PermissionDTO> getPermissionsForRoles(List<RoleDTO> roles) {
        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }

        List<Integer> roleIds = roles.stream()
                .map(RoleDTO::getId)
                .collect(Collectors.toList());

        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT rp.permission FROM RolePermissionsDTO rp WHERE rp.role.id IN (:roleIds)",
                            PermissionDTO.class)
                    .setParameterList("roleIds", roleIds)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch permissions for roles", e);
        }
    }
}
