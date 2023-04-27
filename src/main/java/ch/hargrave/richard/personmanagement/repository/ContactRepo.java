package ch.hargrave.richard.personmanagement.repository;

import ch.hargrave.richard.personmanagement.model.ContactDetails;
import ch.hargrave.richard.personmanagement.model.ContactDetails;
import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepo extends JpaRepository<ContactDetails, Long> {

    public List<ContactDetails> findByEmail(String Email);
    public List<ContactDetails> findByOrderByEmailAsc();
}
