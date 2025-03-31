package org.example.model.DAO.accessControl;

import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class UserDAO {
    private final SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer createUser(UserDTO userDTO) {}
    public UserDTO validateLogin(String username, String password) {}
    public void ActivateAccount(String email) {}
}
