package dat.data.dao;

import dat.data.dao.boilerplate.DAO;
import dat.data.dto.HobbyWithPersonCountDTO;
import dat.model.Hobby;
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

    public List<HobbyWithPersonCountDTO> getAllHobbiesWithPersonCount() {
        try (EntityManager em = super.getEntityManagerFactory().createEntityManager()) {
            return em.createQuery("SELECT new dat.data.dto.HobbyWithPersonCountDTO(h, COUNT(p)) FROM Hobby h LEFT JOIN PersonHobby ph ON h.id = ph.hobby.id LEFT JOIN Person p ON ph.person.id = p.id GROUP BY h.id", HobbyWithPersonCountDTO.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}