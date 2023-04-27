package ch.hargrave.richard.personmanagement.repository;

import ch.hargrave.richard.personmanagement.model.ContactDetails;
import ch.hargrave.richard.personmanagement.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonalRepo extends JpaRepository<PersonalInfo, Long> {

    public List<PersonalInfo> findByName(String name);
    public List<PersonalInfo> findByOrderByNameAsc();
}