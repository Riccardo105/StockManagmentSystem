package org.example.model.DAO.accessControl;

import org.example.model.DTO.AccessControl.PermissionsDTO;
import org.hibernate.SessionFactory;

public class PermissionsAbstractAccessControlDAO extends AbstractAccessControlDAO<PermissionsDTO> {

    public PermissionsAbstractAccessControlDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    protected Class<PermissionsDTO> getDTOClass() {
        return PermissionsDTO.class;
    }
}
