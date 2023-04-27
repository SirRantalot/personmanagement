package ch.hargrave.richard.personmanagement.service;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.City;
import ch.hargrave.richard.personmanagement.repository.CityRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepo cityRepo;

    public CityService(CityRepo cityRepo) {
        this.cityRepo = cityRepo;
    }

    public List<City> getCities() {
        return cityRepo.findByOrderByCityAsc();
    }

    public City getCity(Long id) {
        return cityRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public City addCity(City city) {
        return cityRepo.save(city);
    }

    public City updateCity(City city, Long id) {
        return cityRepo.findById(id)
                .map(cityOG -> {
                    cityOG.setCity(city.getCity());
                    return cityRepo.save(cityOG);
                })
                .orElseGet(() -> cityRepo.save(city));
    }

    public MessageResponse terminateCity(Long id) {
        cityRepo.deleteById(id);
        return new MessageResponse("City " + id + " has been terminated");
    }
}
