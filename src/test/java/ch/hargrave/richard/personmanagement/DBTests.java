package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.repository.CountryRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class DBTests {

    @Autowired
    private CountryRepo countryRepo;

    @Test
    void insertCountry() {
        Country country = this.countryRepo.save(new Country("Switzerland", "Swiss"));
        Assertions.assertNotNull(country.getId());
        Country country1 = this.countryRepo.save(new Country("Germany", "German"));
        Assertions.assertNotNull(country1.getId());
    }
}
