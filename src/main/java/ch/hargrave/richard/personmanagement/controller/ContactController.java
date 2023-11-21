package ch.hargrave.richard.personmanagement.controller;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.ContactDetails;
import ch.hargrave.richard.personmanagement.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.Role;
import java.util.List;

@RestController
@Validated
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("api/contact")
    public ResponseEntity<List<ContactDetails>> getAllContacts() {
        List<ContactDetails> result = contactService.getContactDetails();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("api/contact/{id}")
    public ResponseEntity<ContactDetails> getOne(@PathVariable Long id) {
        ContactDetails contact = contactService.getContact(id);
        return new ResponseEntity<>(contact, HttpStatus.OK);
    }

    @PostMapping("api/contact")
    public ResponseEntity<ContactDetails> newContact(@Valid @RequestBody ContactDetails contact) {
        ContactDetails savedContact = contactService.addContact(contact);
        return  new ResponseEntity<>(savedContact, HttpStatus.OK);
    }

    @PutMapping("api/contact/{id}")
    public ResponseEntity<ContactDetails> putContact(@Valid @RequestBody ContactDetails contact, @PathVariable Long id) {
        ContactDetails savedContact = contactService.updateContact(contact, id);
        return new ResponseEntity<>(savedContact, HttpStatus.OK);
    }

    @DeleteMapping("api/contact/{id}")
    public ResponseEntity<MessageResponse> deleteContact(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(contactService.terminateContact(id));
        } catch (Throwable t) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
