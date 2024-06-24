package ch.hargrave.richard.personmanagement.controller;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.City;
import ch.hargrave.richard.personmanagement.security.Roles;
import ch.hargrave.richard.personmanagement.service.CityService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
@Slf4j
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("api/city")
    @RolesAllowed(Roles.User)
    public ResponseEntity<List<City>> getAll() {
        List<City> result = cityService.getCities();
        log.info("return every city");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/city/{id}")
    @RolesAllowed(Roles.User)
    public ResponseEntity<City> getOne(@PathVariable Long id) {
        City city = cityService.getCity(id);
        log.info("return specific city");
        return new ResponseEntity<>(city, HttpStatus.OK);
    }

    @PostMapping("api/city")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<City> newCity(@Valid @RequestBody City city) {
        City savedCity = cityService.addCity(city);
        log.info("create new city");
        return new ResponseEntity<>(savedCity, HttpStatus.OK);
    }

    @PutMapping("api/city/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<City> putCity(@Valid @RequestBody City city, @PathVariable Long id) {
        City savedCity = cityService.updateCity(city, id);
        log.info("update city");
        return new ResponseEntity<>(savedCity, HttpStatus.OK);
    }

    @DeleteMapping("api/city/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteCity(@PathVariable Long id) {
        try {
            log.info("delete city");
            return ResponseEntity.ok(cityService.terminateCity(id));
        } catch (Throwable t) {
            log.error("error deleting city: ", t);
            return ResponseEntity.internalServerError().build();
        }
    }

}