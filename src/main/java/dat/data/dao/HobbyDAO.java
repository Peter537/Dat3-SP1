package dat.data.dao;

import dat.config.HibernateConfig;
import dat.data.dao.boilerplate.DAO;
import dat.model.Hobby;
import dat.model.HobbyType;
import jakarta.persistence.EntityManager;

import java.util.List;

public class HobbyDAO extends DAO<Hobby> {

    private static HobbyDAO instance;

    private HobbyDAO() { }

    public static HobbyDAO getInstance() {
        if (instance == null) {
            instance = new HobbyDAO();
        }

        return instance;
    }

    public List<Hobby> getHobbiesByType(HobbyType hobbyType) {
        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            List<Hobby> hobbies = entityManager.createQuery("SELECT h FROM Hobby h WHERE h.type = :hobbyType", Hobby.class)
                    .setParameter("hobbyType", hobbyType)
                    .getResultList();
            entityManager.getTransaction().commit();
            return hobbies;
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}