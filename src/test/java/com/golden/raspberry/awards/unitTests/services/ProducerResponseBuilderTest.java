package com.golden.raspberry.awards.unitTests.services;

import com.golden.raspberry.awards.dtos.ProducerResponseDto;
import com.golden.raspberry.awards.dtos.ProducerWinnerResponse;
import com.golden.raspberry.awards.services.ProducerResponseBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProducerResponseBuilderTest {

    private ProducerResponseBuilder producerResponseBuilder;

    @BeforeEach
    void setUp() {
        producerResponseBuilder = new ProducerResponseBuilder();
    }

    @Test
    void testBuildResponse() {
        Map<String, List<Integer>> producerMapYears = new HashMap<>();
        producerMapYears.put("Sean S. Cunningham", Arrays.asList(1983, 1984));
        producerMapYears.put("Allan Carr", Arrays.asList(1980, 1987));
        producerMapYears.put("Matthew Vaughn", Collections.singletonList(2002));

        List<ProducerResponseDto> response = producerResponseBuilder.buildResponse(producerMapYears);

        assertNotNull(response);
        assertEquals(2, response.size());

        ProducerResponseDto sean = response.stream()
                .filter(p -> p.getName().equals("Sean S. Cunningham"))
                .findFirst()
                .orElseThrow();
        assertEquals("Sean S. Cunningham", sean.getName());
        assertEquals(1983, sean.getPreviousWin());
        assertEquals(1984, sean.getFollowingWin());
        assertEquals(1, sean.getInterval());

        ProducerResponseDto allan = response.stream()
                .filter(p -> p.getName().equals("Allan Carr"))
                .findFirst()
                .orElseThrow();
        assertEquals("Allan Carr", allan.getName());
        assertEquals(1980, allan.getPreviousWin());
        assertEquals(1987, allan.getFollowingWin());
        assertEquals(7, allan.getInterval());
    }

    @Test
    void testBuildWinnerResponse() {
        List<ProducerResponseDto> producersResponse = Arrays.asList(
                new ProducerResponseDto("Sean S. Cunningham", 1983, 1984, 1),
                new ProducerResponseDto("Allan Carr", 1980, 1987, 7),
                new ProducerResponseDto("Matthew Vaughn", 2004, 2006, 2)
        );

        ProducerWinnerResponse response = producerResponseBuilder.buildWinnerResponse(producersResponse);

        assertNotNull(response);

        ProducerResponseDto minWinner = response.getMin().get(0);
        assertEquals("Sean S. Cunningham", minWinner.getName());
        assertEquals(1, minWinner.getInterval());

        ProducerResponseDto maxWinner = response.getMax().get(0);
        assertEquals("Allan Carr", maxWinner.getName());
        assertEquals(7, maxWinner.getInterval());
    }

    @Test
    void testBuildWinnerResponseWhenHasTwoMinAndMaxIntervalWinners() {
        List<ProducerResponseDto> producersResponse = Arrays.asList(
                new ProducerResponseDto("Sean S. Cunningham", 1983, 1984, 1),
                new ProducerResponseDto("Allan Carr", 1980, 1987, 7),
                new ProducerResponseDto("Matthew Vaughn", 2004, 2005, 1),
                new ProducerResponseDto("Lester Berman", 1981, 1988, 7)
        );

        ProducerWinnerResponse response = producerResponseBuilder.buildWinnerResponse(producersResponse);

        assertNotNull(response);

        var firstMinWinner = response.getMin().get(0);
        assertEquals("Sean S. Cunningham", firstMinWinner.getName());
        assertEquals(1, firstMinWinner.getInterval());
        var secondMinWinner = response.getMin().get(1);
        assertEquals("Matthew Vaughn", secondMinWinner.getName());
        assertEquals(1, secondMinWinner.getInterval());

        var firstMaxWinner = response.getMax().get(0);
        assertEquals("Allan Carr", firstMaxWinner.getName());
        assertEquals(7, firstMaxWinner.getInterval());
        var secondMaxWinner = response.getMax().get(1);
        assertEquals("Lester Berman", secondMaxWinner.getName());
        assertEquals(7, secondMaxWinner.getInterval());
    }

}
