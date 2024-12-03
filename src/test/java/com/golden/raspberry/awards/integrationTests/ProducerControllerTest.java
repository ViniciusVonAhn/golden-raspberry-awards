package com.golden.raspberry.awards.integrationTests;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ProducerControllerTest extends IntegrationTestConfig {

    private static final String BASE_URI = "/v1/api/producers";

    @Test
    void shouldReturnMinMaxWinners() throws Exception {
        var expected =
                """
                {
                	"min": [
                		{
                			"name": "Sean S. Cunningham",
                			"previousWin": 1983,
                			"followingWin": 1984,
                			"interval": 1
                		}
                	],
                	"max": [
                		{
                			"name": "Allan Carr",
                			"previousWin": 1980,
                			"followingWin": 1987,
                			"interval": 7
                		}
                	]
                }
                """;
        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URI + "/min-max-winners"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expected));
    }
}
