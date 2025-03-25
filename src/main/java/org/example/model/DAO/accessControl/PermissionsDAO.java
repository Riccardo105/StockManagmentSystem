package org.example.model.DAO.accessControl;

import org.example.model.DTO.AccessControl.PermissionsDTO;
import org.hibernate.SessionFactory;

public class PermissionsDAO extends AbstractAccessControlDAO<PermissionsDTO> {

    public PermissionsDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    protected Class<PermissionsDTO> getDTOClass() {
        return PermissionsDTO.class;
    }
}
