package org.example.model.DAO.accessControl;
import com.google.inject.Inject;
import org.example.model.DTO.AccessControl.RoleDTO;
import org.example.model.DTO.AccessControl.UserDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAO {
    private final SessionFactory sessionFactory;

    @Inject
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

    public UserDTO getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM UserDTO WHERE email= :email";
            return session.createQuery(hql, UserDTO.class)
                    .setParameter("email", email)
                    .uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("database operation failed", e);
        }

    }
    public List<UserDTO> getNoneActivatedAccounts() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from UserDTO u where u.activated=false", UserDTO.class)
                    .getResultList();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch not active accounts" + e);
        }

    }
    public void activateAccount(UserDTO user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("update UserDTO u set u.activated=true where u.id=:id")
                    .setParameter("id", user.getId())
                    .executeUpdate();
            tx.commit();
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }


}
