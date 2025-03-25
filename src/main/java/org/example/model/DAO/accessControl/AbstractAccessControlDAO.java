package org.example.model.DAO.accessControl;

import org.example.model.DAO.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public abstract class AbstractAccessControlDAO<T> extends AbstractDAO<T> {


    public AbstractAccessControlDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<T> readAll() {

        Transaction tx = null;
        List<T> resultList;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Class<T> dtoClass = getDTOClass();

            String hql = "FROM " + dtoClass.getSimpleName();
            Query<T> query = session.createQuery(hql, dtoClass);

            resultList = query.getResultList();

            tx.commit();
            return resultList;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Failed to perform read operation", e);
        }
    }
}
