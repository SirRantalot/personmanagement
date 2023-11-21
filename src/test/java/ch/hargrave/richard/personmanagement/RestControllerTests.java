package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.repository.CountryRepo;
import ch.hargrave.richard.personmanagement.service.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.client.RestTemplate;

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
class RestControllerTests {
    @Autowired
    private MockMvc api;

    @Autowired
    private CountryRepo countryRepo;

    @BeforeAll
    void setup() {
        this.countryRepo.save(new Country("Switzerland", "Swiss"));
        this.countryRepo.save(new Country("Germany", "German"));
    }

    @Test
    @Order(1)
    void testGetCountries() throws Exception {

        api.perform(get("/api/country"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Switzerland")));
    }

    @Test
    void testGetCountry() throws Exception {

        api.perform(get("/api/country/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));
    }

    @Test
    void testPostCountry() throws Exception {

        Country country = new Country();
        country.setCountry("Japan");
        country.setNationality("Japanese");
        String body = new ObjectMapper().writeValueAsString(country);

        api.perform(post("/api/country").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Japan")));
    }

    @Test
    void testUpdateCountry() throws Exception {

        Country country = new Country();
        country.setCountry("Japan");
        country.setNationality("Japanese");
        country.setId(33L);
        String body = new ObjectMapper().writeValueAsString(country);

        api.perform(post("/api/country").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Japan")));

        country.setCountry("China");
        country.setNationality("Chinese");
        Long id = country.getId();
        body = new ObjectMapper().writeValueAsString(country);

        api.perform(put("/api/country/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("China")));
    }

    @Test
    void testTerminateCountry() throws Exception {

        Country country = new Country();
        country.setCountry("Japan");
        country.setNationality("Japanese");
        country.setId(33L);
        Long id = country.getId();
        String body = new ObjectMapper().writeValueAsString(country);

        api.perform(post("/api/country").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Japan")));

        api.perform(delete("/api/country/" + id))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().equals(null);
    }


}
