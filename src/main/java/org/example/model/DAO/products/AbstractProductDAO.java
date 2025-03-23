package org.example.model.DAO.products;

import org.example.model.DAO.AbstractDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public abstract class AbstractProductDAO<T> extends AbstractDAO<T> {


    public AbstractProductDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public void update (List<T> dtos) {
        Session session = null;
        Transaction tx = null;

        try{

            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            for (T dto : dtos) {
                session.update(dto);
            }

            tx.commit();
        }catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw new RuntimeException("Failed to update the entity", e);
        }finally{

            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }


}
