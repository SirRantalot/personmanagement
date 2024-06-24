package ch.hargrave.richard.personmanagement.controller;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.security.Roles;
import ch.hargrave.richard.personmanagement.service.CountryService;
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
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("api/country")
    @RolesAllowed(Roles.User)
    public ResponseEntity<List<Country>> getAll() {
        List<Country> result = countryService.getCountries();
        log.info("return every country");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/country/{id}")
    @RolesAllowed(Roles.User)
    public ResponseEntity<Country> getOne(@PathVariable Long id) {
        Country country = countryService.getCountry(id);
        log.info("return specific country");
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping("api/country")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Country> newCountry(@Valid @RequestBody Country country) {
        Country savedCountry = countryService.addCountry(country);
        log.info("create new country");
        return new ResponseEntity<>(savedCountry, HttpStatus.OK);
    }

    @PutMapping("api/country/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Country> putCountry(@Valid @RequestBody Country country, @PathVariable Long id) {
        Country savedCountry = countryService.updateCountry(country, id);
        log.info("update country");
        return new ResponseEntity<>(savedCountry, HttpStatus.OK);
    }

    @DeleteMapping("api/country/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteCountry(@PathVariable Long id) {
        try {
            log.info("delete country");
            return ResponseEntity.ok(countryService.terminateCountry(id));
        } catch (Throwable t) {
            log.error("error while deleting country: ", t);
            return ResponseEntity.internalServerError().build();
        }
    }

}