package ch.hargrave.richard.personmanagement.controller;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.PersonalInfo;
import ch.hargrave.richard.personmanagement.service.PersonalInfoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class PersonalInfoController {

    private final PersonalInfoService personalInfoService;

    public PersonalInfoController(PersonalInfoService personalInfoService) {
        this.personalInfoService = personalInfoService;
    }

    @GetMapping("api/personalInfo")
    public ResponseEntity<List<PersonalInfo>> getAll() {
        List<PersonalInfo> result = personalInfoService.getPersonalInfo();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/personalInfo/{id}")
    public ResponseEntity<PersonalInfo> getOne(@PathVariable Long id) {
        PersonalInfo personalInfo = personalInfoService.getPerson(id);
        return new ResponseEntity<>(personalInfo, HttpStatus.OK);
    }

    @PostMapping("api/personalInfo")
    public ResponseEntity<PersonalInfo> newPerson(@Valid @RequestBody PersonalInfo personalInfo) {
        PersonalInfo savedPerson = personalInfoService.addPerson(personalInfo);
        return  new ResponseEntity<>(savedPerson, HttpStatus.OK);
    }

    @PutMapping("api/personalInfo/{id}")
    public ResponseEntity<PersonalInfo> putPerson(@Valid @RequestBody PersonalInfo personalInfo, @PathVariable Long id) {
        PersonalInfo savedPerson = personalInfoService.updatePerson(personalInfo, id);
        return new ResponseEntity<>(savedPerson, HttpStatus.OK);
    }

    @DeleteMapping("api/personalInfo/{id}")
    public ResponseEntity<MessageResponse> deletePerson(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(personalInfoService.terminatePerson(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
