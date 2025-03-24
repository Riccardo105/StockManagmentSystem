package org.example.model.DAO.accessControl;


import org.example.model.DTO.AccessControl.UserRoleDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserRoleDAO  extends AccessControlDAO<UserRoleDTO> {

    public UserRoleDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    public void createWithCompositeKey(UserRoleDTO dto) {
        Transaction tx = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.save(dto);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Failed to insert UserRole", e);
        }finally {
            if (session != null ){
                session.close();
            }
        }
    }

    protected Class<UserRoleDTO> getDTOClass() {
        return UserRoleDTO.class;
    }
}
