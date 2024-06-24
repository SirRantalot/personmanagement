package ch.hargrave.richard.personmanagement.controller;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.PersonalInfo;
import ch.hargrave.richard.personmanagement.security.Roles;
import ch.hargrave.richard.personmanagement.service.PersonalInfoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
@Slf4j
public class PersonalInfoController {

    private final PersonalInfoService personalInfoService;

    public PersonalInfoController(PersonalInfoService personalInfoService) {
        this.personalInfoService = personalInfoService;
    }

    @GetMapping("api/personalInfo")
    @RolesAllowed(Roles.User)
    public ResponseEntity<List<PersonalInfo>> getAll() {
        List<PersonalInfo> result = personalInfoService.getPersonalInfo();
        log.info("return every personal info");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/personalInfo/{id}")
    @RolesAllowed(Roles.User)
    public ResponseEntity<PersonalInfo> getOne(@PathVariable Long id) {
        PersonalInfo personalInfo = personalInfoService.getPerson(id);
        log.info("return specific personal info");
        return new ResponseEntity<>(personalInfo, HttpStatus.OK);
    }

    @PostMapping("api/personalInfo")
    @RolesAllowed(Roles.User)
    public ResponseEntity<PersonalInfo> newPerson(@Valid @RequestBody PersonalInfo personalInfo) {
        PersonalInfo savedPerson = personalInfoService.addPerson(personalInfo);
        log.info("create new personal info");
        return new ResponseEntity<>(savedPerson, HttpStatus.OK);
    }

    @PutMapping("api/personalInfo/{id}")
    @RolesAllowed(Roles.User)
    public ResponseEntity<PersonalInfo> putPerson(@Valid @RequestBody PersonalInfo personalInfo,
            @PathVariable Long id) {
        PersonalInfo savedPerson = personalInfoService.updatePerson(personalInfo, id);
        log.info("update personal info");
        return new ResponseEntity<>(savedPerson, HttpStatus.OK);
    }

    @DeleteMapping("api/personalInfo/{id}")
    @RolesAllowed(Roles.User)
    public ResponseEntity<MessageResponse> deletePerson(@PathVariable Long id) {
        try {
            log.info("delete personal info");
            return ResponseEntity.ok(personalInfoService.terminatePerson(id));
        } catch (Throwable t) {
            log.error("error while deleting personal info: ", t);
            return ResponseEntity.internalServerError().build();
        }
    }

}