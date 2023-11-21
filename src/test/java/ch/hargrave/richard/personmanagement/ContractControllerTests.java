/*package ch.hargrave.richard.personmanagement;

import ch.hargrave.richard.personmanagement.model.Contract;
import ch.hargrave.richard.personmanagement.repository.ContractRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
class ContractControllerTests {
    @Autowired
    private MockMvc api;

    @Autowired
    private ContractRepo contractRepo;

    @BeforeAll
    void setup() {
        this.contractRepo.save(new Contract("Contract1"));
        this.contractRepo.save(new Contract("Contract2"));
    }

    @Test
    @Order(1)
    void testGetContracts() throws Exception {

        api.perform(get("/api/contract"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Contract1")));
    }

    @Test
    void testGetContract() throws Exception {

        api.perform(get("/api/contract/1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("1")));
    }

    @Test
    void testPostContract() throws Exception {

        Contract contract = new Contract();
        contract.setContract("Contract3");
        String body = new ObjectMapper().writeValueAsString(contract);

        api.perform(post("/api/contract").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Contract3")));
    }

    @Test
    void testUpdateContract() throws Exception {

        Contract contract = new Contract();
        contract.setContract("Contract3");
        contract.setId(33L);
        String body = new ObjectMapper().writeValueAsString(contract);

        api.perform(post("/api/contract").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Contract3")));

        contract.setContract("Contract4");
        Long id = contract.getId();
        body = new ObjectMapper().writeValueAsString(contract);

        api.perform(put("/api/contract/" + id).contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Contract4")));
    }

    @Test
    void testTerminateContract() throws Exception {

        Contract contract = new Contract();
        contract.setContract("Contract3");
        contract.setId(33L);
        Long id = contract.getId();
        String body = new ObjectMapper().writeValueAsString(contract);

        api.perform(post("/api/contract").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Contract3")));

        api.perform(delete("/api/contract/" + id))
                .andDo(print()).andExpect(status().isOk())
                .andReturn().equals(null);
    }
}*/