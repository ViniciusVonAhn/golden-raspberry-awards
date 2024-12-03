package com.golden.raspberry.awards.unitTests.controllers;

import com.golden.raspberry.awards.controllers.ProducerController;
import com.golden.raspberry.awards.dtos.ProducerWinnerResponse;
import com.golden.raspberry.awards.dtos.ProducerResponseDto;
import com.golden.raspberry.awards.services.ProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProducerController.class)
class ProducerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProducerService producerService;

    @Test
    void getMinAndMaxProducerWinners_shouldReturnProducerWinners() throws Exception {
        ProducerResponseDto minProducer = new ProducerResponseDto("Producer A", 1980, 1981, 1);
        ProducerResponseDto maxProducer = new ProducerResponseDto("Producer B", 1990, 2000, 10);

        ProducerWinnerResponse response = new ProducerWinnerResponse(
                List.of(minProducer),
                List.of(maxProducer)
        );

        when(producerService.findMinAndMaxWinners()).thenReturn(response);

        var expected =
                """
                {
                    "min": [
                        {
                            "name": "Producer A",
                            "previousWin": 1980,
                            "followingWin": 1981,
                            "interval": 1
                        }
                    ],
                    "max": [
                        {
                            "name": "Producer B",
                            "previousWin": 1990,
                            "followingWin": 2000,
                            "interval": 10
                        }
                    ]
                }
                """;

        mockMvc.perform(get("/v1/api/producers/min-max-winners")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    void getMinAndMaxProducerWinners_shouldReturnEmptyListsWhenNoWinners() throws Exception {
        ProducerWinnerResponse response = new ProducerWinnerResponse(List.of(), List.of());

        when(producerService.findMinAndMaxWinners()).thenReturn(response);

        var expected =
                """
                {
                    "min": [],
                    "max": []
                }
                """;

        mockMvc.perform(get("/v1/api/producers/min-max-winners")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

}

