package dat.dao;

import dat.config.HibernateConfig;
import dat.data.dao.PersonDAO;
import dat.data.dao.boilerplate.DAO;
import dat.data.dto.PhoneNumbersDTO;
import dat.model.*;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class DaoTest {

    EntityManagerFactory emf;
    PersonDAO personDAO = new PersonDAO();
    DAO<Hobby> hobbyDAO = new DAO<>();
    DAO<Address> addressDAO = new DAO<>();
    


    @BeforeEach
    void setUp() {
        // Create EntityManagerFactory
        emf = HibernateConfig.getEntityManagerFactoryConfig("Hobby");

        // Truncate database
        personDAO.setEntityManagerFactory(emf);
        personDAO.truncate(Person.class);

        hobbyDAO.setEntityManagerFactory(emf);
        hobbyDAO.truncate(Hobby.class);

        addressDAO.setEntityManagerFactory(emf);
        addressDAO.truncate(Address.class);

        // Insert test data

    }

    @Test
    void testGetPerson() {
        // Create new person
        Person person = createTestPerson(1);
        personDAO.save(person);

        // Get person from DB
        Person personFromDB = personDAO.findById(Person.class, person.getId());

        // Check fields
        assertEquals(person.getFirstName(), personFromDB.getFirstName());
    }

    @Test
    void testUpdatePerson() {
        // Create new person
        Person person = createTestPerson(1);
        personDAO.save(person);

        // Update person
        person.setFirstName("Test2");
        personDAO.update(person);

        // Get person from DB
        Person personFromDB = personDAO.findById(Person.class, person.getId());

        // Check fields
        assertEquals(person.getFirstName(), personFromDB.getFirstName());
    }

    @Test
    void testDeletePerson() {
        // Create new person
        Person person = createTestPerson(1);
        personDAO.save(person);

        // Delete person
        personDAO.delete(person);

        // Get all persons from DB
        int count = personDAO.getAll(Person.class).size();

        assertEquals(0, count);
    }

    @Test
    void testGetHobby() {
        // Create new hobby
        Hobby hobby = createTestHobby(1);
        hobbyDAO.save(hobby);

        // Get hobby from DB
        Hobby hobbyFromDB = hobbyDAO.findById(Hobby.class, hobby.getName());

        // Check fields
        assertEquals(hobbyFromDB.getName(), hobby.getName());
    }


    @Test
    void testDeleteHobby() {
        // Create new hobby
        Hobby hobby = createTestHobby(1);
        hobbyDAO.save(hobby);

        // Delete hobby
        hobbyDAO.delete(hobby);

        // Check fields
        assertEquals(0, hobbyDAO.getAll(Hobby.class).size());
    }

    @Test
    void testGetPersonNumbers() {
        // Create new person
        Person person = createTestPerson(1);
        personDAO.save(person);

        // Get phone numbers from person from DB
        PhoneNumbersDTO phoneNumbersDTO = personDAO.getPhoneNumbers(person.getId());

        // Check phone numbers
        assertEquals(person.getId(), phoneNumbersDTO.id());
        assertEquals(person.getHomePhoneNumber(), phoneNumbersDTO.homeNumber());
        assertEquals(person.getWorkPhoneNumber(), phoneNumbersDTO.workNumber());
        assertEquals(person.getMobilePhoneNumber(), phoneNumbersDTO.mobileNumber());
    }

    @Test
    void testGetAllByHobby() {
        // Create new hobby

        // Create 3 new person

        // Set hobby for 2 persons

        // Get all persons with hobby from DB

        // Check persons

        // Check count
    }

    @Test
    void testGetAllHobbies() { // TODO: DTO with hobbies and count of persons (List<HobbyDTO>)
        // Create 3 new hobby

        // Get all hobbies from DB

        // Check hobbies

        // Check count
    }

    @Test
    void testGetAllByZip() {
        // Create 3 new person

        // Set zip for 2 persons

        // Get all persons with zip from DB

        // Check persons
    }

    @Test
    void testGetAllZip() {
        // Get all zip from DB

        // Check List size
    }

    @Test
    void testGetPersonByMobile() {
        // Create 2 new person

        // Set mobile for 2 persons

        // Get person with mobile from DB

        // Check person
    }

    @Test
    void testGetPersonByEmail() {
        // Create 2 new person

        // Set email for 2 persons

        // Get person with email from DB

        // Check person
    }

    @Test
    void testGetHobbiesByType() {
        // Create 3 new hobby

        // Set type for 2 hobbies

        // Get hobbies with type from DB

        // Check hobbies
    }

    @Test
    void testCreateEvent() {
        // Create new event

        // Create new person

        // Add person to event

        // Create new hobby

        // Add hobby to event

        // Get event from DB

        // Check event
    }

    @Test
    void testGetEventByHobby() {
        // Create new event

        // Create new hobby

        // Add hobby to event

        // Get event from DB

        // Check event
    }

    @Test
    void testGetEventByPerson() {
        // Create new event

        // Create new person

        // Add person to event

        // Get event from DB

        // Check event
    }



    protected Person createTestPerson(int uniqueId) {
        Person person = new Person();
        person.setFirstName("Test" + uniqueId);
        person.setLastName("Test" + uniqueId);
        person.setEmail("test" + uniqueId + "@test.dk");
        person.setBirthDate(LocalDate.of(2000, 1, 1));
        person.setHomePhoneNumber("1234567" + uniqueId);
        person.setWorkPhoneNumber("1234567" + uniqueId);
        person.setMobilePhoneNumber("1234567" + uniqueId);
        return person;
    }

    protected Hobby createTestHobby(int uniqueId) {
        return Hobby.builder()
                .name("TestName" + uniqueId)
                .wikiLink("TestWikiLink")
                .type(HobbyType.INDOOR)
                .category(HobbyCategory.GENEREL)
                .build();
    }
}
