package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.ContactDetails;
import ch.hargrave.richard.personmanagement.model.PersonalInfo;
import ch.hargrave.richard.personmanagement.repository.PersonalRepo;
import ch.hargrave.richard.personmanagement.service.PersonalInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PersonalInfoServiceTests {

    private PersonalInfoService personalInfoService;
    private final PersonalRepo personalRepoMock = mock(PersonalRepo.class);

    private final PersonalInfo personalInfoMock = mock(PersonalInfo.class);

    @BeforeEach
    void setUp() {
        personalInfoService = new PersonalInfoService(personalRepoMock);
    }

    @Test
    void createPersonalInfo() {
        when(personalRepoMock.save(personalInfoMock)).thenReturn(personalInfoMock);
        personalInfoService.addPerson(personalInfoMock);
        verify(personalRepoMock, times(1)).save(any());
    }

    @Test
    void findPersonalInfo() {
        when(personalRepoMock.findById(any())).thenReturn(Optional.ofNullable(personalInfoMock));
        PersonalInfo v = personalInfoService.getPerson(any());
        verify(personalRepoMock, times(1)).findById(any());
    }

    @Test
    void updatePersonalInfo() {
        when(personalRepoMock.save(personalInfoMock)).thenReturn(personalInfoMock);
        PersonalInfo newPersonalInfo = mock(PersonalInfo.class);
        personalInfoService.addPerson(personalInfoMock);
        personalInfoService.updatePerson(newPersonalInfo, personalInfoMock.getId());
        verify(personalRepoMock, times(2)).save(any());
    }

    @Test
    void deletePersonalInfo() {
        personalInfoService.terminatePerson(any());
        verify(personalRepoMock, times(1)).deleteById(any());
    }
}