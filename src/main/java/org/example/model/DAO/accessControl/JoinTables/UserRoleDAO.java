package org.example.model.DAO.accessControl.JoinTables;


import org.example.model.DAO.accessControl.AbstractAccessControlDAO;
import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.example.model.DTO.AccessControl.UserRoleDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;

/**
 * UserRoleDAO does not follow standard AbstractDAO
 * due to difference in crud operations for join tables
 */
public class UserRoleDAO  {
    private final SessionFactory sessionFactory;

    public UserRoleDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void assignRole(UserDTO user, RoleDTO role) {}
    public void revokeRole(UserDTO user, RoleDTO role) {}
    public void revokeAllRolesForUser(UserDTO user) {}

    public ArrayList<RoleDTO> getRolesForUser(UserDTO user) {}
    public ArrayList<UserDTO> getUsersForRole (RoleDTO role) {}
    public ArrayList<UserRoleDTO> getUserRolePairs(){}

    public boolean checkUserRoleExists(UserDTO user, RoleDTO role) {}


}
