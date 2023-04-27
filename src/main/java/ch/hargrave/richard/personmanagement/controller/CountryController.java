package ch.hargrave.richard.personmanagement.controller;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.security.Roles;
import ch.hargrave.richard.personmanagement.service.CountryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("api/country")
    @RolesAllowed(Roles.Viewer)
    public ResponseEntity<List<Country>> getAll() {
        List<Country> result = countryService.getCountries();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/country/{id}")
    @RolesAllowed(Roles.Viewer)
    public ResponseEntity<Country> getOne(@PathVariable Long id) {
        Country country = countryService.getCountry(id);
        return new ResponseEntity<>(country, HttpStatus.OK);
    }

    @PostMapping("api/country")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Country> newCountry(@Valid @RequestBody Country country) {
        Country savedCountry = countryService.addCountry(country);
        return  new ResponseEntity<>(savedCountry, HttpStatus.OK);
    }

    @PutMapping("api/country/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<Country> putCountry(@Valid @RequestBody Country country, @PathVariable Long id) {
        Country savedCountry = countryService.updateCountry(country, id);
        return new ResponseEntity<>(savedCountry, HttpStatus.OK);
    }

    @DeleteMapping("api/country/{id}")
    @RolesAllowed(Roles.Admin)
    public ResponseEntity<MessageResponse> deleteCountry(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(countryService.terminateCountry(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
