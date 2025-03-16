package org.example.model.DAO.products;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public abstract class AbstractDAO<T> {
    private final SessionFactory sessionFactory;

    public AbstractDAO( SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer create (T dto) {
        Session session = null;
        Transaction tx = null;
        Integer generatedId = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Save the object and get the generated ID
            generatedId = (Integer) session.save(dto);

            // Commit the transaction
            tx.commit();
        } catch (Exception e) {
            // Roll back the transaction if an exception occurs
            if (tx != null) {
                tx.rollback();
            }
            // Log the exception or rethrow it
            throw new RuntimeException("Failed to insert the entity", e);
        } finally {
            // Close the session in the finally block
            if (session != null) {
                session.close();
            }
        }
        return generatedId;
    }

    public T read (int id) {
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Perform the read operation
            T dto = session.get(getDTOClass(), id);

            // Commit the transaction
            tx.commit();
            return dto;
        } catch (Exception e) {
            // Roll back the transaction if an exception occurs
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            // Log the exception or rethrow it
            throw new RuntimeException("Failed to read the entity", e);
        } finally {
            // Close the session in the finally block
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void update (T dto) {
        Session session = null;
        Transaction tx = null;
        try {
            // Open a session and begin a transaction
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Perform the update operation
            session.update(dto);

            // Commit the transaction
            tx.commit();
        } catch (Exception e) {
            // Roll back the transaction in case of an exception
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            // Log the exception or rethrow it as a custom exception
            throw new RuntimeException("Failed to update the entity", e);
        } finally {
            // Close the session in the finally block to ensure it's always closed
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public void update (List<T> dtos) {
        Session session = null;
        Transaction tx = null;

        try{
            // Open a session and begin a transaction
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Perform the update operation
            for (T dto : dtos) {
                session.update(dto);
            }
            // Commit the transaction
            tx.commit();
        }catch (Exception e) {
            // Roll back the transaction in case of an exception
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            // Log the exception or rethrow it as a custom exception
            throw new RuntimeException("Failed to update the entity", e);
        }finally{
            // Close the session in the finally block to ensure it's always closed
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

    }

    public void delete (T dto) {
        Session session = null;
        Transaction tx = null;
        try{
            // Open a session and begin a transaction
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // perform delete operation
            session.delete(dto);

            // Commit the transaction
            tx.commit();
        }catch (Exception e) {
            // Roll back the transaction if an exception occurs
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            // Log the exception or rethrow it
            throw new RuntimeException("Failed to delete the entity", e);
        }finally{
            // Close the session in the finally block to ensure it's always closed
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    protected abstract Class<T> getDTOClass();


}
