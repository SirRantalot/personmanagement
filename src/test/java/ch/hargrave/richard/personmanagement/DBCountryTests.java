package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.Country;
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
class DBCountryTests {

    @Autowired
    private CountryRepo countryRepo;

    Long id1 = 300L;
    Long id2 = 300L;

    @Test
    void insertCountry() {
        Country country = countryRepo.save(new Country("Switzerland", "Swiss", id1));
        Assertions.assertNotNull(country.getId());
        Country country1 = countryRepo.save(new Country("Germany", "German", id2));
        Assertions.assertNotNull(country1.getId());

        countryRepo.deleteById(country.getId());
        countryRepo.deleteById(country1.getId());
    }

    @Test
    void getCountry() {
        Country country = countryRepo.save(new Country("Switzerland", "Swiss", id1));
        Assertions.assertNotNull(country.getId());

        Country countryFound = countryRepo.findById(country.getId()).get();

        Assertions.assertEquals(countryFound, country);

        countryRepo.deleteById(country.getId());
    }

    @Test
    void updateCountry() {
        Country country = this.countryRepo.save(new Country("Switzerland", "Swiss", id1));

        Country updatedCounty = new Country("England", "English", country.getId());

        Country country1 = countryRepo.findById(country.getId()).get();
        country1.setCountry(updatedCounty.getCountry());
        country1.setNationality(updatedCounty.getNationality());
        countryRepo.save(country1);

        Assertions.assertEquals(country1.getId(), country.getId());
        Assertions.assertEquals(country1.getCountry(), updatedCounty.getCountry());
        Assertions.assertEquals(country1.getNationality(), updatedCounty.getNationality());
        countryRepo.deleteById(country.getId());
        countryRepo.deleteById(country1.getId());
    }

    @Test
    void terminateCountry() {
        Country country = countryRepo.save(new Country("Switzerland", "Swiss", id1));

        Assertions.assertNotNull(countryRepo.findById(country.getId()));
        Assertions.assertFalse(countryRepo.findById(country.getId()).isEmpty());
        countryRepo.deleteById(country.getId());
        Assertions.assertTrue(countryRepo.findById(country.getId()).isEmpty());

    }
}
