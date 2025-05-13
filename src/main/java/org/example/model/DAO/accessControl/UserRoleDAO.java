package org.example.model.DAO.accessControl;
import com.google.inject.Inject;

import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.example.model.DTO.AccessControl.UserRoleDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

    @Inject
    public UserRoleDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    // needed by Activate Account window
    public List<RoleDTO> getAvailableRoles(){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from RoleDTO", RoleDTO.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void assignRoleToUser(UserDTO user, RoleDTO role) {
        UserRoleDTO userRole = new UserRoleDTO(user, role);

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.save(userRole);
            tx.commit();
        }catch (RuntimeException e) {
            throw e;
        }

    }


public List<RoleDTO> getRolesForUser(UserDTO user) {
    try (Session session = sessionFactory.openSession()) {
        // First get direct roles
        List<RoleDTO> directRoles = session.createQuery(
                        "SELECT ur.role FROM UserRoleDTO ur WHERE ur.user.id = :userId",
                        RoleDTO.class)
                .setParameter("userId", user.getId())
                .getResultList();

        if (directRoles.isEmpty()) {
            return directRoles;
        }

        // Convert to list of IDs
        List<Integer> roleIds = directRoles.stream()
                .map(RoleDTO::getId)
                .collect(Collectors.toList());

        // Get all descendant roles recursively using native SQL
        // Note: Changed :roleIds to ?1 for positional parameter
        List<RoleDTO> descendantRoles = session.createNativeQuery("""
        WITH RECURSIVE role_hierarchy AS (
            -- Base case: start with direct roles
            SELECT r.* FROM role r WHERE r.id IN (?1)
            
            UNION ALL
            
            -- Recursive case: find all roles that are children of roles in the hierarchy
            SELECT r.* FROM role r
            JOIN role_hierarchy rh ON r.id = rh.childRoleId
            WHERE rh.childRoleId IS NOT NULL
        )
        SELECT * FROM role_hierarchy
        """, RoleDTO.class)
                .setParameter(1, roleIds)  // Using positional parameter
                .getResultList();

        // Combine and remove duplicates
        Set<RoleDTO> allRoles = new LinkedHashSet<>();
        allRoles.addAll(directRoles);
        allRoles.addAll(descendantRoles);

        return new ArrayList<>(allRoles);
    } catch (Exception e) {
        throw new RuntimeException("Failed to get roles for user ID: " + user.getId(), e);
    }
}
}


