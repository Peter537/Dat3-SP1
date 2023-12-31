package dat.dao;

import dat.config.HibernateConfig;
import dat.data.dao.EventDAO;
import dat.data.dao.HobbyDAO;
import dat.data.dao.PersonDAO;
import dat.data.dao.boilerplate.DAO;
import dat.data.dto.HobbyWithPersonCountDTO;
import dat.data.dto.PhoneNumbersDTO;
import dat.model.*;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DaoTest {

    private final PersonDAO personDAO = PersonDAO.getInstance();
    private final HobbyDAO hobbyDAO = HobbyDAO.getInstance();
    private final EventDAO eventDAO = EventDAO.getInstance();

    private final DAO<Address> addressDAO = new DAO<>();
    private final DAO<Zip> zipDAO = new DAO<>();

    @BeforeEach
    void setUp() {
        // Create EntityManagerFactory
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("Hobby");

        // Truncate database
        personDAO.setEntityManagerFactory(emf);
        personDAO.truncate(Person.class);

        hobbyDAO.setEntityManagerFactory(emf);
        hobbyDAO.truncate(Hobby.class);

        addressDAO.setEntityManagerFactory(emf);
        addressDAO.truncate(Address.class);

        zipDAO.setEntityManagerFactory(emf);
        zipDAO.truncate(Zip.class);

        eventDAO.setEntityManagerFactory(emf);
        eventDAO.truncate(Event.class);
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
    void testGetAllByHobby() {
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
    void getAllHobbiesWithPersonCount() {
        // Create 3 new hobby
        Hobby hobby1 = createTestHobby(1);
        Hobby hobby2 = createTestHobby(2);
        Hobby hobby3 = createTestHobby(3);
        hobbyDAO.save(hobby1);
        hobbyDAO.save(hobby2);
        hobbyDAO.save(hobby3);

        // Create 3 new person
        Person person1 = createTestPerson(1);
        Person person2 = createTestPerson(2);
        Person person3 = createTestPerson(3);
        personDAO.save(person1);
        personDAO.save(person2);
        personDAO.save(person3);

        // Set hobby for 2 persons
        person1.addHobby(hobby1, 5);
        person2.addHobby(hobby1, 3);
        personDAO.update(person1);
        personDAO.update(person2);
        for (PersonHobby hobby : person1.getHobbies()) {
            System.out.println(hobby.getHobby().getName());
        }

        // Get all hobbies with person count from DB
        List<HobbyWithPersonCountDTO> hobbies = hobbyDAO.getAllHobbiesWithPersonCount();

        // Check hobbies
        assertEquals(3, hobbies.size());
        for (HobbyWithPersonCountDTO hobby : hobbies) {
            if (hobby.hobby().getName().equals("TestName1")) {
                assertEquals(2, hobby.count());
            } else {
                assertEquals(0, hobby.count());
            }
        }
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
        // Create 3 new zip
        Zip zip1 = new Zip(3300, "Yes", "Yep", "Yup");
        Zip zip2 = new Zip(3301, "Yes", "Yep", "Yup");
        Zip zip3 = new Zip(3302, "Yes", "Yep", "Yup");
        zipDAO.save(zip1);
        zipDAO.save(zip2);
        zipDAO.save(zip3);

        // Get all zip from DB
        List<Zip> zips = zipDAO.getAll(Zip.class);

        // Check List size
        assertEquals(3, zips.size());
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
        person1.setEmail("a@a.dk");
        person2.setEmail("b@b.dk");
        personDAO.save(person1);
        personDAO.save(person2);

        // Get person with email from DB
        Person person = personDAO.getPersonByEmail("a@a.dk");

        // Check person
        assertEquals(person1.getId(), person.getId());
    }

    @Test
    void testGetHobbiesByType() {
        // Create 3 new hobby
        Hobby hobby1 = createTestHobby(1);
        Hobby hobby2 = createTestHobby(2);
        Hobby hobby3 = createTestHobby(3);
        hobbyDAO.save(hobby1);
        hobbyDAO.save(hobby2);
        hobbyDAO.save(hobby3);

        // Get hobbies with type from DB
        List<Hobby> hobbies = hobbyDAO.getHobbiesByType(HobbyType.INDOOR);

        // Check hobbies
        assertEquals(3, hobbies.size());
    }

    @Test
    void testCreateEvent() {
        // Create new event
        Event event = new Event();
        eventDAO.save(event);

        // Create new person
        Person person = createTestPerson(1);
        personDAO.save(person);

        // Add person to event
        event.getAttendees().add(person);
        eventDAO.update(event);

        // Create new hobby
        Hobby hobby = createTestHobby(1);
        hobbyDAO.save(hobby);

        // Add hobby to event
        event.setHobby(hobby);
        eventDAO.update(event);

        // Create new address and zip
        Zip zip = new Zip(3300, "Yes", "Yep", "Yup");
        zipDAO.save(zip);

        Address address = new Address("TestStreet", zip);
        addressDAO.save(address);

        // Add address to event
        event.setAddress(address.getStreet());
        eventDAO.update(event);

        Event eventDB = eventDAO.findById(Event.class, event.getId());
        assertEquals(event.getId(), eventDB.getId());
    }

    @Test
    void testJoinEvent() {
        // Create event
        Event event = new Event();
        eventDAO.save(event);

        // Create new person
        Person person = createTestPerson(1);
        personDAO.save(person);

        // Add person to event
        event.getAttendees().add(person);
        eventDAO.update(event);

        // Create new hobby
        Hobby hobby = createTestHobby(1);
        hobbyDAO.save(hobby);

        // Add hobby to event
        event.setHobby(hobby);
        eventDAO.update(event);

        // Create new address and zip
        Zip zip = new Zip(3300, "Yes", "Yep", "Yup");
        zipDAO.save(zip);

        Address address = new Address("TestStreet", zip);
        addressDAO.save(address);

        // Add address to event
        event.setAddress(address.getStreet());
        eventDAO.update(event);

        // Add event to person
        assertEquals(1, event.getAttendees().size());
    }

    @Test
    void testGetEventsByHobby() {
        // Create new event
        Person person = createTestPerson(1);
        personDAO.save(person);

        Hobby hobby = createTestHobby(1);
        hobbyDAO.save(hobby);

        Zip zip = new Zip(3300, "Yes", "Yep", "Yup");
        zipDAO.save(zip);

        Address address = new Address("TestStreet", zip);
        addressDAO.save(address);
        Event event = new Event(person, hobby, address.getStreet(), "Test", 0.0, LocalDate.now());
        eventDAO.save(event);

        // Get event from DB
        List<Event> events = eventDAO.getEventsByHobby(hobby);

        // Check event
        assertEquals(1, events.size());
        assertEquals(event.getName(), events.get(0).getName());
    }

    @Test
    void testGetEventByPerson() {
        // Create new event
        Event event = new Event();
        eventDAO.save(event);

        // Create new person
        Person person = createTestPerson(1);
        personDAO.save(person);

        // Add person to event
        event.setCreatedBy(person);
        eventDAO.update(event);

        // Create new hobby
        Hobby hobby = createTestHobby(1);
        hobbyDAO.save(hobby);

        // Add hobby to event
        event.setHobby(hobby);
        eventDAO.update(event);

        // Create new address and zip
        Zip zip = new Zip(3300, "Yes", "Yep", "Yup");
        zipDAO.save(zip);

        Address address = new Address("TestStreet", zip);
        addressDAO.save(address);

        // Add address to event
        event.setAddress(address.getStreet());
        eventDAO.update(event);

        //Get event from DB and check
        List<Event> personFromDB = eventDAO.getEventsByPerson(person);
        assertEquals(person.getId(), personFromDB.get(0).getCreatedBy().getId());
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