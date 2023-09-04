package dat.data.dao;

import dat.data.dao.boilerplate.DAO;
import dat.model.Event;
import dat.model.Hobby;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EventDAO extends DAO<Event> {

    private static EventDAO instance;

    private EventDAO() {
    }

    public static EventDAO getInstance() {
        if (instance == null) {
            instance = new EventDAO();
        }

        return instance;
    }

    public List<Event> getEventsByHobby(Hobby hobby) {
        try (EntityManager em = super.getEntityManagerFactory().createEntityManager()) {
            return em.createQuery("SELECT e FROM Event e WHERE e.hobby = :hobby", Event.class)
                    .setParameter("hobby", hobby)
                    .getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}