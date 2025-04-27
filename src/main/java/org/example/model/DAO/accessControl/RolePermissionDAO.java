package org.example.model.DAO.accessControl;
import org.example.model.DTO.AccessControl.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.google.inject.Inject;

public class RolePermissionDAO {
    private final SessionFactory sessionFactory;

    @Inject
    public RolePermissionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<PermissionsDTO> getPermissionsForRole(RoleDTO role) {
        if (role == null) {
            return Collections.emptyList();
        }

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT rp.permission FROM RolePermissionsDTO rp WHERE rp.role.id = :roleId",
                            PermissionsDTO.class)
                    .setParameter("roleId", role.getId())
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch permissions for role ID: " + role.getId(), e);
        }
    }

    public List<PermissionsDTO> getPermissionsForRoles(List<RoleDTO> roles) {
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
                            PermissionsDTO.class)
                    .setParameterList("roleIds", roleIds)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch permissions for roles", e);
        }
    }
}
