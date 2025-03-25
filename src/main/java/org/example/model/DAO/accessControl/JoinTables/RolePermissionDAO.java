package org.example.model.DAO.accessControl.JoinTables;
import org.example.model.DAO.accessControl.AbstractAccessControlDAO;
import org.example.model.DTO.AccessControl.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class RolePermissionDAO {
    private final SessionFactory sessionFactory;

    public RolePermissionDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void assignPermission(RoleDTO role, PermissionsDTO permission) {}
    public void revokePermission(RoleDTO role, PermissionsDTO permission) {}
    public void revokeAllPermissionForRole(RoleDTO role) {}

    public ArrayList<PermissionsDTO> getPermissionForRole(RoleDTO role) {}
    public ArrayList<RoleDTO> getRoleForPermission (PermissionsDTO permission) {}
    public ArrayList<RolePermissionsDTO> getRolePermissionPairs(){}

    public boolean checkRolePermissionExists(RoleDTO role, PermissionsDTO permission) {}
}
