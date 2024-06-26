package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.City;
import ch.hargrave.richard.personmanagement.repository.CityRepo;
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
class CityControllerTests {
    @Autowired
    private MockMvc api;

    @Autowired
    private CityRepo cityRepo;

    @Test
    void testGetCity() throws Exception {
        City city = new City("Zurich");
        this.cityRepo.save(city);

        api.perform(get("/api/city"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Zurich")));

        this.cityRepo.delete(city);
    }


    @Test
    void testPostUpdateDeleteCity() throws Exception {
        City city = new City("Tokyo");
        String body = new ObjectMapper().writeValueAsString(city);

        MvcResult mvcResult = api.perform(post("/api/city").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Tokyo")))
                .andReturn();

        Long id = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), City.class).getId();

        api.perform(put("/api/city/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new City("Beijing"))))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Beijing")));

        api.perform(delete("/api/city/" + id))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().equals(null);
    }
}