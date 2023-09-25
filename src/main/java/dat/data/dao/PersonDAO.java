package dat.data.dao;

import dat.data.dao.boilerplate.DAO;
import dat.data.dto.PhoneNumbersDTO;
import dat.model.Hobby;
import dat.model.Person;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PersonDAO extends DAO<Person> {

    private static PersonDAO instance;

    private PersonDAO() { }

    public static PersonDAO getInstance() {
        if (instance == null) {
            instance = new PersonDAO();
        }

        return instance;
    }

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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public List<Person> getAllByZip(Integer zip) {
        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            List<Person> persons = entityManager.createQuery("SELECT p FROM Person p LEFT JOIN p.address address WHERE address IS NOT NULL OR address.zip.", Person.class)
                    .setParameter("zip", zip)
                    .getResultList();
            entityManager.getTransaction().commit();
            return persons;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public Person getAllByMobile(String number) {
        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.mobilePhoneNumber = :number", Person.class)
                    .setParameter("number", number)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return person;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public List<Person> getAllByHobby(Hobby hobby) {
        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            List<Person> persons = entityManager.createQuery("SELECT DISTINCT p FROM Person p JOIN p.hobbies ph WHERE ph.hobby = :hobby", Person.class)
                    .setParameter("hobby", hobby)
                    .getResultList();
            entityManager.getTransaction().commit();
            return persons;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public Person getPersonByEmail(String mail) {
        try (EntityManager entityManager = super.getEntityManagerFactory().createEntityManager()) {
            entityManager.getTransaction().begin();
            Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.email = :mail", Person.class)
                    .setParameter("mail", mail)
                    .getSingleResult();
            entityManager.getTransaction().commit();
            return person;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}