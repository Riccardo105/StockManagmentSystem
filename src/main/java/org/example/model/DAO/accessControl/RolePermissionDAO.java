package org.example.model.DAO.accessControl;
import org.example.model.DTO.AccessControl.RolePermissionsDTO;
import org.hibernate.SessionFactory;

public class RolePermissionDAO extends AccessControlDAO<RolePermissionsDTO> {

    public RolePermissionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<RolePermissionsDTO> getDTOClass() {
        return RolePermissionsDTO.class;
    }
}
