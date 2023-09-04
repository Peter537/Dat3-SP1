package dat.dao.boilerplate;

import jakarta.persistence.EntityManagerFactory;

import java.util.List;

/**
 * This is an interface for making a DAO (Data Access Object) that can be used to perform CRUD operations on any entity.
 *
 * @param <T>
 */
interface IDAO<T> {

    /**
     *
     * @param emf
     */
    void setEntityManagerFactory(EntityManagerFactory emf);

    /**
     *
     * @param tClass
     * @param id
     * @return
     */
    T findById(Class<T> tClass, Object id);

    /**
     *
     * @param tClass
     * @return
     */
    List<T> getAll(Class<T> tClass);

    /**
     *
     * @param t
     * @return
     */
    Boolean save(T t);

    /**
     *
     * @param t
     * @return
     */
    T update(T t);

    /**
     *
     * @param t
     */
    void delete(T t);

    /**
     *
     * @param tClass
     */
    void truncate(Class<T> tClass);

    /**
     *
     */
    void close();
}