package ch.hargrave.richard.personmanagement.controller;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.City;
import ch.hargrave.richard.personmanagement.service.CityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;

@RestController
@Validated
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("api/city")
    public ResponseEntity<List<City>> getAll() {
        List<City> result = cityService.getCities();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/city/{id}")
    public ResponseEntity<City> getOne(@PathVariable Long id) {
        City city = cityService.getCity(id);
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @PostMapping("api/city")
    public ResponseEntity<City> newCity(@Valid @RequestBody City city) {
        City savedCity = cityService.addCity(city);
        return  new ResponseEntity<>(savedCity, HttpStatus.OK);
    }

    @PutMapping("api/city/{id}")
    public ResponseEntity<City> putCity(@Valid @RequestBody City city, @PathVariable Long id) {
        City savedCity = cityService.updateCity(city, id);
        return new ResponseEntity<>(savedCity, HttpStatus.OK);
    }

    @DeleteMapping("api/city/{id}")
    public ResponseEntity<MessageResponse> deleteCity(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(cityService.terminateCity(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
