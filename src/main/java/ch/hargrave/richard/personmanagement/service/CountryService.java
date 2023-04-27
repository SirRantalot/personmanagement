package ch.hargrave.richard.personmanagement.service;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.repository.CountryRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    private final CountryRepo countryRepo;

    public CountryService(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    public List<Country> getCountries() {
        return countryRepo.findByOrderByCountryAsc();
    }

    public Country getCountry(Long id) {
        return countryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public Country addCountry(Country country) {
        return countryRepo.save(country);
    }

    public Country updateCountry(Country country, Long id) {
        return countryRepo.findById(id)
                .map(countryOG -> {
                    countryOG.setCountry(country.getCountry());
                    countryOG.setNationality(country.getNationality());
                    return countryRepo.save(countryOG);
                })
                .orElseGet(() -> countryRepo.save(country));
    }

    public MessageResponse terminateCountry(Long id) {
        countryRepo.deleteById(id);
        return new MessageResponse("Country "+id+" has been terminated");
    }
}
