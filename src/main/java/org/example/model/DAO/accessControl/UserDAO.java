package org.example.model.DAO.accessControl;

import org.example.model.DTO.AccessControl.PermissionsDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO {
    private final SessionFactory sessionFactory;

    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Integer create(UserDTO userDTO) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Integer generatedId = (Integer) session.save(userDTO);
            tx.commit();
            return generatedId;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create new account " + e);
        }
    }

    public UserDTO getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM UserDTO WHERE username = :username";
            return session.createQuery(hql, UserDTO.class)
                    .setParameter("username", username)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("no account found for the username" + e);
        }

    }
    public List<UserDTO> getNoneActivatedAccounts() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from UserDTO u where u.activated=false", UserDTO.class)
                    .getResultList();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch not actived accounts" + e);
        }

    }
    public void ActivateAccount(UserDTO user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("update UserDTO u set u.activated=true where u.id=:id")
                    .setParameter("id", user.getId())
                    .executeUpdate();
            tx.commit();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to activate account " + e);
        }

    }
}
