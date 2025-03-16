package org.example.DAO.products;
import org.example.config.DbConnection;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

/**
 * for tests sessionFactory is connection to Testing instance of the database
 * @param <T> takes in the DAO's corresponding DTO
 */
public abstract class AbstractDAOUnitTest<T> {
    protected static SessionFactory sessionFactory;
    protected Session session;

    // session factory instantiated before tests
    @BeforeAll
    public static void setUpSessionFactory() {
        sessionFactory = DbConnection.getSessionFactory();
        System.out.println("SessionFactoryCreated");
    }

    // session factory shutdown after tests
    @AfterAll
    public static void tearDownSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("SessionFactory Closed");
        }
    }

    /**
     * this helper is used for CRUD operations that require an instance in the database.
     * @param session passed by tests
     * @return instance's id: after save operation Hibernate captures the auto-generated id and returns it
     * (remember to run the save operation as assignment of variable:  Integer id = session.save(object))
     */
    protected abstract Integer HelperCreateDTO(Session session);

    /**
     * this helper is used by all tests to clear up after themselves.
     * delete operation is carried regardless of assertions pass.
     * @param session passed by tests
     * @param dto object to delete
     *
     */
    protected abstract void HelperDeleteDTO(Session session, T dto);

    /**
     * Testing correct insertion of object to database.
     * testing that the inheritance strategy (see ProductDTO) works as intended.
     * thus we are asserting one field from each table
     */
    public abstract void testCreate();

    /**
     * testing correct read of object from database.
     * the id is capture by the return of the helperCreateDTO.
     * we are asserting one field from each table.
     */
    public abstract void testRead();
    public abstract void testDelete();
    public abstract void testUpdate();
    public abstract void testUpdateList();


}
