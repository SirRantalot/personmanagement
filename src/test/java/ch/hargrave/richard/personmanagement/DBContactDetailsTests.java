package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.ContactDetails;
import ch.hargrave.richard.personmanagement.model.City;
import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.repository.ContactRepo;
import ch.hargrave.richard.personmanagement.repository.CityRepo;
import ch.hargrave.richard.personmanagement.repository.CountryRepo;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class DBContactDetailsTests {

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private CountryRepo countryRepo;

    Long id1 = 300L;
    Long id2 = 300L;

    @Test
    void insertContactDetails() {
        City city = cityRepo.save(new City("Zurich"));
        Country country = countryRepo.save(new Country("Switzerland", "Swiss"));
        ContactDetails contactDetails = contactRepo
                .save(new ContactDetails("Street", "1", "email@example.com", 12345, "1234567890", city, country));
        Assertions.assertNotNull(contactDetails.getId());

        // ... rest of your tests

        contactRepo.deleteById(contactDetails.getId());
        cityRepo.deleteById(city.getId());
        countryRepo.deleteById(country.getId());
    }

    // ... rest of your test methods

    @Test
    void getContactDetails() {
        City city = cityRepo.save(new City("Zurich"));
        Country country = countryRepo.save(new Country("Switzerland", "Swiss"));
        ContactDetails contactDetails = contactRepo.save(new ContactDetails("Street", "1", "email@example.com", 12345,
                "1234567890", city, country));
        Assertions.assertNotNull(contactDetails.getId());

        ContactDetails contactDetailsFound = contactRepo.findById(contactDetails.getId()).get();

        Assertions.assertEquals(contactDetailsFound, contactDetails);

        contactRepo.deleteById(contactDetails.getId());
        cityRepo.deleteById(city.getId());
        countryRepo.deleteById(country.getId());
    }

    @Test
    void updateContactDetails() {
        City city = cityRepo.save(new City("Zurich"));
        Country country = countryRepo.save(new Country("Switzerland", "Swiss"));
        ContactDetails contactDetails = this.contactRepo.save(new ContactDetails("Street", "1", "email@example.com",
                12345, "1234567890", city, country));

        ContactDetails updatedContactDetails = new ContactDetails("Street", "1", "updatedEmail@example.com", 12345,
                "1234567890", city, country);
        updatedContactDetails.setId(contactDetails.getId());

        ContactDetails contactDetails1 = contactRepo.findById(contactDetails.getId()).get();
        contactDetails1.setEmail(updatedContactDetails.getEmail());
        contactRepo.save(contactDetails1);

        Assertions.assertEquals(contactDetails1.getId(), contactDetails.getId());
        Assertions.assertEquals(contactDetails1.getEmail(), updatedContactDetails.getEmail());
        contactRepo.deleteById(contactDetails.getId());
        cityRepo.deleteById(city.getId());
        countryRepo.deleteById(country.getId());
    }

    @Test
    void terminateContactDetails() {
        City city = cityRepo.save(new City("Zurich"));
        Country country = countryRepo.save(new Country("Switzerland", "Swiss"));
        ContactDetails contactDetails = contactRepo.save(new ContactDetails("Street", "1", "email@example.com", 12345,
                "1234567890", city, country));

        Assertions.assertNotNull(contactRepo.findById(contactDetails.getId()));
        Assertions.assertFalse(contactRepo.findById(contactDetails.getId()).isEmpty());
        contactRepo.deleteById(contactDetails.getId());
        Assertions.assertTrue(contactRepo.findById(contactDetails.getId()).isEmpty());
        cityRepo.deleteById(city.getId());
        countryRepo.deleteById(country.getId());
    }
}