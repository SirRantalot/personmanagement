package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.City;
import ch.hargrave.richard.personmanagement.model.ContactDetails;
import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.repository.CityRepo;
import ch.hargrave.richard.personmanagement.repository.ContactRepo;
import ch.hargrave.richard.personmanagement.repository.CountryRepo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactDetailsControllerTests {
    @Autowired
    private MockMvc api;

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private CityRepo cityRepo;

    @Autowired
    private CountryRepo countryRepo;

    @BeforeAll
    void setup() {
        City city = cityRepo.save(new City("CityName")); // replace with actual city name
        Country country = countryRepo.save(new Country("CountryName", "Nationality")); // replace with actual country name and nationality
        this.contactRepo
                .save(new ContactDetails("Street1", "1", "email1@example.com", 12345, "1234567890", city, country));
        this.contactRepo
                .save(new ContactDetails("Street2", "2", "email2@example.com", 67890, "0987654321", city, country));
    }

    @Test
    void testGetContacts() throws Exception {
        api.perform(get("/api/contact"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Street1")));
    }

    @Test
    void testGetContact() throws Exception {
        api.perform(get("/api/contact/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Street1")));
    }

    @Test
    void testPostContact() throws Exception {
        City city = new City(); // replace with actual city
        Country country = new Country(); // replace with actual country
        ContactDetails contact = new ContactDetails("Street3", "3", "email3@example.com", 11111, "1111111111", city,
                country);
        String body = new ObjectMapper().writeValueAsString(contact);

        api.perform(post("/api/contact").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Street3")));
    }

                
    void testUpdateContact() throws Exception {
        City city = new City(); // replace with actual city
        Country country = new Country(); // replace with actual country
        ContactDetails contact = new ContactDetails("Street3", "3", "email3@example.com", 11111, "1111111111", city, country);
        contact.setId(33L);
        String body = new ObjectMapper().writeValueAsString(contact);

        api.perform(post("/api/contact").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Street3")));

        contact.setStreet("Street4");
        Long id = contact.getId();
        body = new ObjectMapper().writeValueAsString(contact);

        api.perform(put("/api/contact/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Street4")));
    }

    @Test
    void testTerminateContact() throws Exception {
        api.perform(delete("/api/contact/1"))
                .andDo(print()).andExpect(status().isOk());
    }
}