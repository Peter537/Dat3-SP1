package dat.data.dao;

import dat.config.HibernateConfig;
import dat.data.dao.boilerplate.DAO;
import dat.data.dto.PhoneNumbersDTO;
import dat.model.Person;
import jakarta.persistence.EntityManager;

public class PersonDAO extends DAO<Person> {

    public PhoneNumbersDTO getPhoneNumbers(int id) {
        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            Person person = entityManager.find(Person.class, id);
            PhoneNumbersDTO phoneNumbersDTO = new PhoneNumbersDTO(
                    person.getId(),
                    person.getWorkPhoneNumber(),
                    person.getHomePhoneNumber(),
                    person.getMobilePhoneNumber()
            );
            entityManager.getTransaction().commit();
            return phoneNumbersDTO;
        }
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
