package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.City;
import ch.hargrave.richard.personmanagement.repository.CityRepo;
import ch.hargrave.richard.personmanagement.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CityServiceTests {

    private CityService cityService;
    private final CityRepo cityRepositoryMock = mock(CityRepo.class);

    private final City cityMock = mock(City.class);

    @BeforeEach
    void setUp() {
        cityService = new CityService(cityRepositoryMock);
    }

    @Test
    void createCity() {
        when(cityRepositoryMock.save(cityMock)).thenReturn(cityMock);
        cityService.addCity(cityMock);
        verify(cityRepositoryMock, times(1)).save(any());
    }

    @Test
    void findCity() {
        when(cityRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(cityMock));
        City v = cityService.getCity(any());
        verify(cityRepositoryMock, times(1)).findById(any());
    }

    @Test
    void updateCity() {
        when(cityRepositoryMock.save(cityMock)).thenReturn(cityMock);
        City newCity = mock(City.class);
        cityService.addCity(cityMock);
        cityService.updateCity(newCity, cityMock.getId());
        verify(cityRepositoryMock, times(2)).save(any());
    }

    @Test
    void deleteCity() {
        cityService.terminateCity(any());
        verify(cityRepositoryMock, times(1)).deleteById(any());
    }
}