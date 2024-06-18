package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.Country;
import ch.hargrave.richard.personmanagement.repository.CountryRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
class CountryControllerTests {
    @Autowired
    private MockMvc api;

    @Autowired
    private CountryRepo countryRepo;

    @Test
    void testGetCountry() throws Exception {
        Country country = new Country("Switzerland", "Swiss");
        this.countryRepo.save(country);

        api.perform(get("/api/country"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Switzerland")));

        this.countryRepo.delete(country);
    }

    @Test
    void testPostUpdateDeleteCountry() throws Exception {
        Country country = new Country("Switzerland", "Swiss");
        String body = new ObjectMapper().writeValueAsString(country);

        MvcResult mvcResult = api.perform(post("/api/country").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Switzerland")))
                .andReturn();

        Long id = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Country.class).getId();

        api.perform(put("/api/country/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new Country("Germany", "German"))))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Germany")));

        api.perform(delete("/api/country/" + id))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().equals(null);
    }
}
