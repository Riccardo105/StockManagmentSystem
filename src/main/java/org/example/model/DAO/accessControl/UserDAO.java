package org.example.model.DAO.accessControl;

import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.SessionFactory;

public class UserAbstractAccessControlDAO extends AbstractAccessControlDAO<UserDTO> {

    public UserAbstractAccessControlDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<UserDTO> getDTOClass() {
        return UserDTO.class;
    }

}
