package org.example.model.DAO.accessControl;


import org.example.model.DTO.AccessControl.UserRoleDTO;
import org.hibernate.SessionFactory;

public class UserRoleDAO  extends AccessControlDAO<UserRoleDTO> {

    public UserRoleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<UserRoleDTO> getDTOClass() {
        return UserRoleDTO.class;
    }
}
