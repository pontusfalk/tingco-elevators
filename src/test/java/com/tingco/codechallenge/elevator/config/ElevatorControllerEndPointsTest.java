package com.tingco.codechallenge.elevator.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * Boiler plate test class to get up and running with a test faster.
 *
 * @author Sven Wesley
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class ElevatorControllerEndPointsTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void ping() throws Exception {
        mvc.perform(get("/rest/v1/ping"))
           .andExpect(content().string("pong"));
    }
}
