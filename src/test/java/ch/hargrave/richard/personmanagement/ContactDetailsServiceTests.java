package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.ContactDetails;
import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.repository.ContactRepo;
import ch.hargrave.richard.personmanagement.repository.CountryRepo;
import ch.hargrave.richard.personmanagement.service.ContactService;
import ch.hargrave.richard.personmanagement.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContactDetailsServiceTests {

    private ContactService contactService;
    private final ContactRepo contactRepositoryMock = mock(ContactRepo.class);

    private final ContactDetails contactMock = mock(ContactDetails.class);

    @BeforeEach
    void setUp() {
        contactService = new ContactService(contactRepositoryMock);
    }

    @Test
    void createContact() {
        when(contactRepositoryMock.save(contactMock)).thenReturn(contactMock);
        contactService.addContact(contactMock);
        verify(contactRepositoryMock, times(1)).save(any());
    }

    @Test
    void findContact() {
        when(contactRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(contactMock));
        ContactDetails v = contactService.getContact(any());
        verify(contactRepositoryMock, times(1)).findById(any());
    }

    @Test
    void updateContact() {
        when(contactRepositoryMock.save(contactMock)).thenReturn(contactMock);
        ContactDetails newContact = mock(ContactDetails.class);
        contactService.addContact(contactMock);
        contactService.updateContact(newContact, contactMock.getId());
        verify(contactRepositoryMock, times(2)).save(any());
    }

    @Test
    void deleteContact() {
        contactService.terminateContact(any());
        verify(contactRepositoryMock, times(1)).deleteById(any());
    }
}