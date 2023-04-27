package ch.hargrave.richard.personmanagement.service;

import ch.hargrave.richard.personmanagement.base.MessageResponse;
import ch.hargrave.richard.personmanagement.model.PersonalInfo;
import ch.hargrave.richard.personmanagement.repository.PersonalRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalInfoService {

    private final PersonalRepo personalRepo;

    public PersonalInfoService(PersonalRepo personalRepo) {
        this.personalRepo = personalRepo;
    }

    public List<PersonalInfo> getPersonalInfo() {
        return personalRepo.findByOrderByNameAsc();
    }

    public PersonalInfo getPerson(Long id) {
        return personalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
    }

    public PersonalInfo addPerson(PersonalInfo  personal) {
        return personalRepo.save(personal);
    }

    public PersonalInfo  updatePerson(PersonalInfo  personal, Long id) {
        return personalRepo.findById(id)
                .map(personalOG -> {
                    personalOG.setAge(personal.getAge());
                    personalOG.setName(personal.getName());
                    personalOG.setFirstname(personal.getFirstname());
                    personalOG.setNationality(personal.getNationality());
                    personalOG.setCotactDetails(personal.getCotactDetails());
                    return personalRepo.save(personalOG);
                })
                .orElseGet(() -> personalRepo.save(personal));
    }

    public MessageResponse terminatePerson(Long id) {
        personalRepo.deleteById(id);
        return new MessageResponse("Person "+id+" has been terminated");
    }
}
