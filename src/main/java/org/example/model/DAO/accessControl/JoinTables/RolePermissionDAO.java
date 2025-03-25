package org.example.model.DAO.accessControl;
import org.example.model.DTO.AccessControl.RolePermissionsDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class RolePermissionAbstractAccessControlDAO extends AbstractAccessControlDAO<RolePermissionsDTO> {

    public RolePermissionAbstractAccessControlDAO(SessionFactory sessionFactory) {
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
