package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.repository.CountryRepo;
import ch.hargrave.richard.personmanagement.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CountryServiceTests {

    private CountryService countryService;
    private final CountryRepo countryRepositoryMock = mock(CountryRepo.class);

    private final Country countryMock = mock(Country.class);

    @BeforeEach
    void setUp() {
        countryService = new CountryService(countryRepositoryMock);
    }

    @Test
    void createCountry() {
        when(countryRepositoryMock.save(countryMock)).thenReturn(countryMock);
        countryService.addCountry(countryMock);
        verify(countryRepositoryMock, times(1)).save(any());
    }

    @Test
    void findCountry() {
        when(countryRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(countryMock));
        Country v = countryService.getCountry(any());
        verify(countryRepositoryMock, times(1)).findById(any());
    }

    @Test
    void updateCountry() {
        when(countryRepositoryMock.save(countryMock)).thenReturn(countryMock);
        Country newCountry = mock(Country.class);
        countryService.addCountry(countryMock);
        countryService.updateCountry(newCountry, countryMock.getId());
        verify(countryRepositoryMock, times(2)).save(any());
    }

    @Test
    void deleteCountry() {
        countryService.terminateCountry(any());
        verify(countryRepositoryMock, times(1)).deleteById(any());
    }
}