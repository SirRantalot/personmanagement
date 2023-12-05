package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.City;
import ch.hargrave.richard.personmanagement.repository.CityRepo;
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
class DBCityTests {

    @Autowired
    private CityRepo cityRepo;

    Long id1 = 300L;
    Long id2 = 300L;

    @Test
    void insertCity() {
        City city = cityRepo.save(new City("Zurich"));
        Assertions.assertNotNull(city.getId());
        City city1 = cityRepo.save(new City("Berlin"));
        Assertions.assertNotNull(city1.getId());

        cityRepo.deleteById(city.getId());
        cityRepo.deleteById(city1.getId());
    }

    @Test
    void getCity() {
        City city = cityRepo.save(new City("Zurich"));
        Assertions.assertNotNull(city.getId());

        City cityFound = cityRepo.findById(city.getId()).get();

        Assertions.assertEquals(cityFound, city);

        cityRepo.deleteById(city.getId());
    }

    @Test
    void updateCity() {
        City city = this.cityRepo.save(new City("Zurich"));

        City updatedCity = new City("London");
        updatedCity.setId(city.getId());

        City city1 = cityRepo.findById(city.getId()).get();
        city1.setCity(updatedCity.getCity());
        cityRepo.save(city1);

        Assertions.assertEquals(city1.getId(), city.getId());
        Assertions.assertEquals(city1.getCity(), updatedCity.getCity());
        cityRepo.deleteById(city.getId());
    }

    @Test
    void terminateCity() {
        City city = cityRepo.save(new City("Zurich"));

        Assertions.assertNotNull(cityRepo.findById(city.getId()));
        Assertions.assertFalse(cityRepo.findById(city.getId()).isEmpty());
        cityRepo.deleteById(city.getId());
        Assertions.assertTrue(cityRepo.findById(city.getId()).isEmpty());
    }
}