package org.example.model.DAO.accessControl;

import org.example.model.DTO.AccessControl.OperationDTO;
import org.hibernate.SessionFactory;

public class OperationDAO extends AbstractAccessControlDAO<OperationDTO> {

    public OperationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<OperationDTO> getDTOClass() {
        return OperationDTO.class;
    }
}
