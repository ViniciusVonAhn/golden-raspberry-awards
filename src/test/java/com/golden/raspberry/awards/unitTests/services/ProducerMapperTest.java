package com.golden.raspberry.awards.unitTests.services;

import com.golden.raspberry.awards.dtos.ProducerWinnerDto;
import com.golden.raspberry.awards.services.ProducerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProducerMapperTest {

    private ProducerMapper producerMapper;

    @BeforeEach
    void setUp() {
        producerMapper = new ProducerMapper();
    }

    @Test
    void testMapToProducerYears() {
        Set<ProducerWinnerDto> producersWinner = new HashSet<>();
        producersWinner.add(new ProducerWinnerDto("Sean S. Cunningham", 1983));
        producersWinner.add(new ProducerWinnerDto("Sean S. Cunningham", 1984));
        producersWinner.add(new ProducerWinnerDto("Allan Carr", 1980));
        producersWinner.add(new ProducerWinnerDto("Allan Carr", 1987));
        producersWinner.add(new ProducerWinnerDto("Matthew Vaughn", 2002));

        Map<String, List<Integer>> result = producerMapper.mapToProducerYears(producersWinner);

        assertNotNull(result);
        assertEquals(3, result.size());

        assertTrue(result.containsKey("Sean S. Cunningham"));
        List<Integer> seanYears = result.get("Sean S. Cunningham");
        assertEquals(2, seanYears.size());
        assertTrue(seanYears.contains(1983));
        assertTrue(seanYears.contains(1984));

        assertTrue(result.containsKey("Allan Carr"));
        List<Integer> allanYears = result.get("Allan Carr");
        assertEquals(2, allanYears.size());
        assertTrue(allanYears.contains(1980));
        assertTrue(allanYears.contains(1987));

        assertTrue(result.containsKey("Matthew Vaughn"));
        List<Integer> matthewYears = result.get("Matthew Vaughn");
        assertEquals(1, matthewYears.size());
        assertTrue(matthewYears.contains(2002));
    }
}
