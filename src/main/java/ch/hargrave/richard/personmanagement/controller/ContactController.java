package ch.hargrave.richard.personmanagement.controller;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.ContactDetails;
import ch.hargrave.richard.personmanagement.security.Roles;
import ch.hargrave.richard.personmanagement.service.ContactService;
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
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("api/contact")
    @RolesAllowed(Roles.User)
    public ResponseEntity<List<ContactDetails>> getAllContacts() {
        List<ContactDetails> result = contactService.getContactDetails();
        log.info("return every contract");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/contact/{id}")
    @RolesAllowed(Roles.User)
    public ResponseEntity<ContactDetails> getOne(@PathVariable Long id) {
        ContactDetails contact = contactService.getContact(id);
        log.info("return specific contract");
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PostMapping("api/contact")
    @RolesAllowed(Roles.User)
    public ResponseEntity<ContactDetails> newContact(@Valid @RequestBody ContactDetails contact) {
        ContactDetails savedContact = contactService.addContact(contact);
        log.info("create new contact");
        return new ResponseEntity<>(savedContact, HttpStatus.OK);
    }

    @PutMapping("api/contact/{id}")
    @RolesAllowed(Roles.User)
    public ResponseEntity<ContactDetails> putContact(@Valid @RequestBody ContactDetails contact,
            @PathVariable Long id) {
        ContactDetails savedContact = contactService.updateContact(contact, id);
        log.info("update contact");
        return new ResponseEntity<>(savedContact, HttpStatus.OK);
    }

    @DeleteMapping("api/contact/{id}")
    @RolesAllowed(Roles.User)
    public ResponseEntity<MessageResponse> deleteContact(@PathVariable Long id) {
        try {
            log.info("delete contact");
            return ResponseEntity.ok(contactService.terminateContact(id));
        } catch (Throwable t) {
            log.error("error while deleting contact: ", t);
            return ResponseEntity.internalServerError().build();
        }
    }

}