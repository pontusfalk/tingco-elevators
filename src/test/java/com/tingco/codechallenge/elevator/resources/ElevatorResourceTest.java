package com.tingco.codechallenge.elevator.resources;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//todo: this test is at the moment mostly checking wiring, not doing too much proper validation

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ElevatorResourceTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getConfig() throws Exception {
        mvc.perform(get("/api/elevators/config"))
           .andExpect(status().isOk());
    }

    @Test
    public void getAllElevators() throws Exception {
        mvc.perform(get("/api/elevators"))
           .andExpect(status().isOk());
    }

    @Test
    public void requestElevator() throws Exception {
        mvc.perform(post("/api/elevators/floors/4"))
           .andExpect(status().isOk());
    }

    @Test
    public void moveElevator() throws Exception {
        mvc.perform(post("/api/elevators/floors/1")).andReturn();

        //todo: really bad test: hardcoded  elevator id, get it from request instead!
        mvc.perform(put("/api/elevators/2")
                      .contentType(APPLICATION_JSON)
                      .content("{\"addressedFloor\": 4}"))
           .andExpect(status().isOk());
    }

    @Test
    public void releaseElevator() throws Exception {
        mvc.perform(post("/api/elevators/floors/1")).andReturn();

        //todo: really bad test: hardcoded  elevator id, get it from request instead!
        mvc.perform(delete("/api/elevators/1"))
           .andExpect(status().isOk());
    }
}
