package ch.hargrave.richard.personmanagement.repository;

import ch.hargrave.richard.personmanagement.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<City, Long> {

    public List<City> findByCity(String city);
    public List<City> findByOrderByCityAsc();
}