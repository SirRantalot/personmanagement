package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.City;
import ch.hargrave.richard.personmanagement.repository.CityRepo;
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
class CityControllerTests {
    @Autowired
    private MockMvc api;

    @Autowired
    private CityRepo cityRepo;

    @BeforeAll
    void setup() {
        this.cityRepo.save(new City("Zurich"));
        this.cityRepo.save(new City("Berlin"));
    }

    @Test
    @Order(1)
    void testGetCities() throws Exception {

        api.perform(get("/api/city"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Zurich")));
    }

    @Test
    void testGetCity() throws Exception {

        api.perform(get("/api/city/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));
    }

    @Test
    void testPostCity() throws Exception {

        City city = new City("Zurich");
        city.setCity("Tokyo");
        String body = new ObjectMapper().writeValueAsString(city);

        api.perform(post("/api/city").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Tokyo")));
    }

    @Test
    void testUpdateCity() throws Exception {

        City city = new City("Zurich");
        city.setCity("Tokyo");
        city.setId(33L);
        String body = new ObjectMapper().writeValueAsString(city);

        api.perform(post("/api/city").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Tokyo")));

        city.setCity("Beijing");
        Long id = city.getId();
        body = new ObjectMapper().writeValueAsString(city);

        api.perform(put("/api/city/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Beijing")));
    }

    @Test
    void testTerminateCity() throws Exception {

        City city = new City("Zurich");
        city.setCity("Tokyo");
        city.setId(33L);
        Long id = city.getId();
        String body = new ObjectMapper().writeValueAsString(city);

        api.perform(post("/api/city").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Tokyo")));

        api.perform(delete("/api/city/" + id))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().equals(null);
    }
}