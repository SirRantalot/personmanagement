/*package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.Contract;
import ch.hargrave.richard.personmanagement.model.PersonalInfo;
import ch.hargrave.richard.personmanagement.repository.ContractRepo;
import ch.hargrave.richard.personmanagement.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ContractServiceTests {

    private ContractService contractService;
    private final ContractRepo contractRepositoryMock = mock(ContractRepo.class);

    private final Contract contractMock = mock(Contract.class);
    private final PersonalInfo personalInfoMock = mock(PersonalInfo.class);

    @BeforeEach
    void setUp() {
        contractService = new ContractService(contractRepositoryMock);
    }

    @Test
    void createContract() {
        when(contractMock.getPersonalInfo()).thenReturn(personalInfoMock);
        when(contractRepositoryMock.save(contractMock)).thenReturn(contractMock);
        contractService.addContract(contractMock);
        verify(contractRepositoryMock, times(1)).save(any());
    }

    @Test
    void findContract() {
        when(contractRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(contractMock));
        Contract v = contractService.getContract(any());
        verify(contractRepositoryMock, times(1)).findById(any());
    }

    @Test
    void updateContract() {
        when(contractMock.getPersonalInfo()).thenReturn(personalInfoMock);
        when(contractRepositoryMock.save(contractMock)).thenReturn(contractMock);
        Contract newContract = mock(Contract.class);
        contractService.addContract(contractMock);
        contractService.updateContract(newContract, contractMock.getId());
        verify(contractRepositoryMock, times(2)).save(any());
    }

    @Test
    void deleteContract() {
        contractService.terminateContract(any());
        verify(contractRepositoryMock, times(1)).deleteById(any());
    }
}*/