package ch.hargrave.richard.personmanagement.repository;

import ch.hargrave.richard.personmanagement.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepo extends JpaRepository<Country, Long> {

    public List<Country> findByCountry(String country);
    public List<Country> findByOrderByCountryAsc();

}
