package com.golden.raspberry.awards.unitTests.services;

import com.golden.raspberry.awards.dtos.ProducerResponseDto;
import com.golden.raspberry.awards.dtos.ProducerWinnerDto;
import com.golden.raspberry.awards.dtos.ProducerWinnerResponse;
import com.golden.raspberry.awards.services.MovieService;
import com.golden.raspberry.awards.services.ProducerMapper;
import com.golden.raspberry.awards.services.ProducerResponseBuilder;
import com.golden.raspberry.awards.services.ProducerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProducerServiceTest {

    @Mock
    private MovieService movieService;

    @Mock
    private ProducerMapper producerMapper;

    @Mock
    private ProducerResponseBuilder producerResponseBuilder;

    @InjectMocks
    private ProducerService producerService;

    @Test
    void testFindMinAndMaxWinners() {
        var producerWinnerDtos = Set.of(
                new ProducerWinnerDto("Sean S. Cunningham", 1983),
                new ProducerWinnerDto("Allan Carr", 1980),
                new ProducerWinnerDto("Allan Carr", 1987)
        );

        when(movieService.findWinners()).thenReturn(producerWinnerDtos);

        Map<String, List<Integer>> producersMapYears = Map.of(
                "Sean S. Cunningham", List.of(1983),
                "Allan Carr", Arrays.asList(1980, 1987)
        );
        when(producerMapper.mapToProducerYears(producerWinnerDtos)).thenReturn(producersMapYears);

        List<ProducerResponseDto> producersResponse = Arrays.asList(
                new ProducerResponseDto("Sean S. Cunningham", 1983, 1984, 1),
                new ProducerResponseDto("Allan Carr", 1980, 1987, 7),
                new ProducerResponseDto("Matthew Vaughn", 2002, 2002, 0)
        );
        when(producerResponseBuilder.buildResponse(producersMapYears)).thenReturn(producersResponse);

        ProducerWinnerResponse expectedResponse = new ProducerWinnerResponse(
                Collections.singletonList(producersResponse.get(0)),
                Collections.singletonList(producersResponse.get(1))
        );
        when(producerResponseBuilder.buildWinnerResponse(producersResponse)).thenReturn(expectedResponse);

        ProducerWinnerResponse response = producerService.findMinAndMaxWinners();

        assertNotNull(response);
        assertEquals(1, response.getMin().size());
        assertEquals("Sean S. Cunningham", response.getMin().get(0).getName());
        assertEquals(1, response.getMin().get(0).getInterval());

        assertEquals(1, response.getMax().size());
        assertEquals("Allan Carr", response.getMax().get(0).getName());
        assertEquals(7, response.getMax().get(0).getInterval());

        verify(movieService, times(1)).findWinners();
        verify(producerMapper, times(1)).mapToProducerYears(producerWinnerDtos);
        verify(producerResponseBuilder, times(1)).buildResponse(producersMapYears);
        verify(producerResponseBuilder, times(1)).buildWinnerResponse(producersResponse);
    }
}

