package dat.dao;

import dat.dao.boilerplate.DAO;
import dat.model.Address;
import dat.model.Hobby;
import dat.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DaoTest {

    @BeforeEach
    void setUp() {
        // Truncate database
        DAO<Person> personDAO = new DAO<>();
        personDAO.truncate(Person.class);

        DAO<Hobby> hobbyDAO = new DAO<>();
        hobbyDAO.truncate(Hobby.class);

        DAO<Address> addressDAO = new DAO<>();
        addressDAO.truncate(Address.class);

        // Insert test data

    }

    @Test
    void testGetPerson() {
        // Create new person

        // Get person from DB

        // Check fields
    }

    @Test
    void getHobby() {
        // Create new hobby

        // Get hobby from DB

        // Check fields
    }

    @Test
    void testGetPersonNumbers() { // TODO: DTO with phone numbers and ID
        // Create new person

        // Get phone numbers from person from DB

        // Check phone numbers
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
    void getAllZip() {
        // Get all zip from DB

        // Check List size
    }

    @Test
    void getPersonByMobile() {
        // Create 2 new person

        // Set mobile for 2 persons

        // Get person with mobile from DB

        // Check person
    }

    @Test
    void getPersonByEmail() {
        // Create 2 new person

        // Set email for 2 persons

        // Get person with email from DB

        // Check person
    }
}
