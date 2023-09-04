package dat.dao;

import dat.config.HibernateConfig;
import dat.data.dao.HobbyDAO;
import dat.data.dao.PersonDAO;
import dat.data.dao.boilerplate.DAO;
import dat.data.dto.PhoneNumbersDTO;
import dat.model.*;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DaoTest {

    EntityManagerFactory emf;
    PersonDAO personDAO = new PersonDAO();
    HobbyDAO hobbyDAO = new HobbyDAO();
    DAO<Address> addressDAO = new DAO<>();
    DAO<Zip> zipDAO = new DAO<>();


    @BeforeEach
    void setUp() {
        // Create EntityManagerFactory
        emf = HibernateConfig.getEntityManagerFactoryConfig("hobby");

        // Truncate database
        personDAO.setEntityManagerFactory(emf);
        personDAO.truncate(Person.class);

        hobbyDAO.setEntityManagerFactory(emf);
        hobbyDAO.truncate(Hobby.class);

        addressDAO.setEntityManagerFactory(emf);
        addressDAO.truncate(Address.class);

        zipDAO.setEntityManagerFactory(emf);
        zipDAO.truncate(Zip.class);
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
        assertEquals(person.getLastName(), personFromDB.getLastName());
        assertEquals(person.getEmail(), personFromDB.getEmail());
        assertEquals(person.getBirthDate(), personFromDB.getBirthDate());
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
    void testGetAllByHobby() { // TODO: DTO with hobbies and count of persons (List<HobbyDTO>)
        // Create new hobby
        Hobby hobby = createTestHobby(1);
        hobbyDAO.save(hobby);

        // Create 3 new person
        Person person1 = createTestPerson(1);
        Person person2 = createTestPerson(2);
        Person person3 = createTestPerson(3);
        personDAO.save(person1);
        personDAO.save(person2);
        personDAO.save(person3);

        // Set hobby for 2 persons
        person1.addHobby(hobby, 5);
        person2.addHobby(hobby, 3);
        personDAO.update(person1);
        personDAO.update(person2);

        // Get all persons with hobby from DB
        List<Person> persons = personDAO.getAllByHobby(hobby);

        // Check count
        assertEquals(2, persons.size());
    }

    @Test
    void testGetAllHobbies() {
        // Create 3 new hobby
        Hobby hobby1 = createTestHobby(1);
        Hobby hobby2 = createTestHobby(2);
        Hobby hobby3 = createTestHobby(3);

        hobbyDAO.save(hobby1);
        hobbyDAO.save(hobby2);
        hobbyDAO.save(hobby3);

        // Get all hobbies from DB
        List<Hobby> hobbies = hobbyDAO.getAll(Hobby.class);

        // Check hobbies
        assertEquals(3, hobbies.size());
    }

    @Test
    void testGetAllByZip() {
        // Create 3 new person
        Person person1 = createTestPerson(1);
        Person persen2 = createTestPerson(2);
        Person person3 = createTestPerson(3);
        personDAO.save(person1);
        personDAO.save(persen2);
        personDAO.save(person3);

        // Set zip for 2 persons
        Zip zip = new Zip(3300, "Yes", "Yep", "Yup");
        System.out.println(zipDAO.save(zip));
        Address address = new Address("TestStreet", zip);
        System.out.println(addressDAO.save(address));
        person1.setAddress(address);
        persen2.setAddress(address);
        personDAO.update(person1);
        personDAO.update(persen2);
        System.out.println(person1);

        // Get all persons with zip from DB
        List<Person> persons = personDAO.getAllByZip(3330);

        // Check persons
        assertEquals(2, persons.size());
    }

    @Test
    void testGetAllZip() {
        // Get all zip from DB

        // Check List size
    }

    @Test
    void testGetPersonByMobile() {
        // Create 2 new person
        Person person1 = createTestPerson(1);
        Person person2 = createTestPerson(2);
        personDAO.save(person1);
        personDAO.save(person2);

        // Set mobile for 2 persons
        person1.setMobilePhoneNumber("12345678");
        person2.setMobilePhoneNumber("12345679");
        personDAO.update(person1);
        personDAO.update(person2);

        // Get person with mobile from DB
        Person person = personDAO.getAllByMobile("12345678");

        // Check person
        assertEquals(person1.getId(), person.getId());
    }

    @Test
    void testGetPersonByEmail() {
        // Create 2 new person
        Person person1 = createTestPerson(1);
        Person person2 = createTestPerson(2);

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
