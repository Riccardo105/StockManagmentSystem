package org.example.model.DAO.products;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public abstract class AbstractProductDAO<T>{
    protected final SessionFactory sessionFactory;

    public AbstractProductDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public Integer create (T dto) {
        Session session = null;
        Transaction tx = null;
        Integer generatedId;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            generatedId = (Integer) session.save(dto);

            tx.commit();
        } catch (Exception e) {

            if (tx != null) {
                tx.rollback();
            }

            throw new RuntimeException("Failed to insert the entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return generatedId;
    }

    public T read (Integer id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            T dto = session.get(getDTOClass(), id);

            tx.commit();
            return dto;
        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw new RuntimeException("Failed to read the entity", e);
        } finally {

            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void update (T dto) {
        Session session = null;
        Transaction tx = null;
        try {

            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.update(dto);

            tx.commit();
        } catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw new RuntimeException("Failed to update the entity", e);
        } finally {

            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public void delete (T dto) {
        Session session = null;
        Transaction tx = null;
        try{

            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.delete(dto);

            tx.commit();
        }catch (Exception e) {

            if (tx != null && tx.isActive()) {
                tx.rollback();
            }

            throw new RuntimeException("Failed to delete the entity", e);
        }finally{

            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void delete (List<T> dtos) {
        Session session = null;
        Transaction tx = null;

        try{

            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            for (T dto : dtos) {
                session.delete(dto);
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

    /**
     *
     * @return *subclass corresponding DTO class type.*
     * This is required by the read operation (see above) due to java's runtime type erasure.
     *  all type generics (T) are defaulted to "Object" at runtime if not provided with a type.
     * Thus, each subclass needs to set its corresponding DTO class type so that the object of correct type can be read
     */
    protected abstract Class<T> getDTOClass();

}
