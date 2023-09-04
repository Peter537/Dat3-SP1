package dat.DAO.boilerplate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.exception.ConstraintViolationException;

import javax.lang.model.UnknownEntityException;
import java.util.List;

/**
 * This class is a generic DAO (Data Access Object) that can be used to perform CRUD operations on any entity.
 * @param <T> The entity class that the DAO should be used for.
 */
public class DAO<T> extends ADAO<T> {
}

/**
 * This is an abstract class that is used to perform CRUD operations on any entity. It can be extended to gain access to basic CRUD operations.
 * @param <T>
 */
abstract class ADAO<T> implements IDAO<T> { // TODO: Add tcf (try, catch, final) to all methods that use the entityManager, to ensure it is closed
    EntityManagerFactory emf;

    // Setters
    public void setEntityManagerFactory(EntityManagerFactory emf) {
        this.emf = emf;
    }

    // Queries
    public T findById(Class<T> tClass, int id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.find(tClass, id);

        }
        catch (UnknownEntityException e) {
            System.out.println("Unknown entity: " + tClass.getSimpleName());
            return null;
        }
    }

    public List<T> getAll(Class<T> tClass) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager.createQuery("SELECT t FROM " + tClass.getSimpleName() + " t", tClass).getResultList();
        }
        catch (UnknownEntityException e) {
            System.out.println("Unknown entity: " + tClass.getSimpleName());
            return null;
        }
    }

    // Standard CRUD operations
    public Boolean save(T t) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(t);
            entityManager.getTransaction().commit();
            return true;
        }
        catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
            return false;
        }
    }

    public T update(T t) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            T t1 = entityManager.merge(t);
            entityManager.getTransaction().commit();
            return t1;
        }
        catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
            return null;
        }
    }

    public void delete(T t) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.merge(t)); // Merge to ensure the entity is in the managed state
            entityManager.getTransaction().commit();
        }
        catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
        }
    }

    public void truncate(Class<T> tClass) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            entityManager.getTransaction().begin();

            String tableName = tClass.getSimpleName();
            String sql = "TRUNCATE TABLE " + tableName + " RESTART IDENTITY CASCADE"; // CASCADE drops dependent objects

            entityManager.createNativeQuery(sql).executeUpdate();

            entityManager.getTransaction().commit();

            // Restart sequence
            entityManager.getTransaction().begin();

            sql = "ALTER SEQUENCE " + tableName + "_id_seq RESTART WITH 1";

            entityManager.createNativeQuery(sql).executeUpdate();

            entityManager.getTransaction().commit();
        }
        catch (ConstraintViolationException e) {
            System.out.println("Constraint violation: " + e.getMessage());
        }
    }

    public void close() {
        emf.close();
    }
}

/**
 * This is an interface for making a DAO (Data Access Object) that can be used to perform CRUD operations on any entity.
 * @param <T>
 */
interface IDAO<T> {
    void setEntityManagerFactory(EntityManagerFactory emf);
    T findById(Class<T> tClass, int id);
    List<T> getAll(Class<T> tClass);
    Boolean save(T t);
    T update(T t);
    void delete(T t);
    void truncate(Class<T> tClass);
    void close();
}
