package org.example.model.DAO.accessControl;

import org.example.model.DTO.AccessControl.RoleDTO;
import org.hibernate.SessionFactory;

public class RoleAbstractAccessControlDAO extends AbstractAccessControlDAO<RoleDTO> {

    public RoleAbstractAccessControlDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<RoleDTO> getDTOClass() {
        return RoleDTO.class;
    }
}
