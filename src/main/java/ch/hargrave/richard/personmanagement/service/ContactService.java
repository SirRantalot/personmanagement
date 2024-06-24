package ch.hargrave.richard.personmanagement.service;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.ContactDetails;
import ch.hargrave.richard.personmanagement.repository.ContactRepo;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepo contactRepo;

    public List<ContactDetails> getContactDetails() {
        return contactRepo.findByOrderByEmailAsc();
    }

    public ContactDetails getContact(Long id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public ContactDetails addContact(ContactDetails  contact) {
        return contactRepo.save(contact);
    }

    public ContactDetails  updateContact(ContactDetails  contact, Long id) {
        return contactRepo.findById(id)
                .map(contactOG -> {
                    contactOG.setCity(contact.getCity());
                    contactOG.setCountry(contact.getCountry());
                    contactOG.setStreet(contact.getStreet());
                    contactOG.setStreetNumber(contact.getStreetNumber());
                    contactOG.setPostalNumber(contact.getPostalNumber());
                    contactOG.setPhoneNumber(contact.getPhoneNumber());
                    contactOG.setEmail(contact.getEmail());
                    return contactRepo.save(contactOG);
                })
                .orElseGet(() -> contactRepo.save(contact));
    }

    public MessageResponse terminateContact(Long id) {
        contactRepo.deleteById(id);
        return new MessageResponse("Contact "+id+" has been terminated");
    }
}
