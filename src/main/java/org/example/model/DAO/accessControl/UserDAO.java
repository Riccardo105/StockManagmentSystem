package org.example.model.DAO.accessControl;

import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.SessionFactory;

public class UserDAO extends AbstractAccessControlDAO<UserDTO> {

    public UserDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    protected Class<UserDTO> getDTOClass() {
        return UserDTO.class;
    }

}
