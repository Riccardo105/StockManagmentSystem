package org.example.model.DAO.accessControl;
import org.example.model.DTO.AccessControl.RolePermissionsDTO;
import org.example.model.DTO.AccessControl.UserRoleDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class RolePermissionDAO extends AccessControlDAO<RolePermissionsDTO> {

    public RolePermissionDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void createWithCompositeKey(RolePermissionsDTO dto) {
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

    protected Class<RolePermissionsDTO> getDTOClass() {
        return RolePermissionsDTO.class;
    }
}
