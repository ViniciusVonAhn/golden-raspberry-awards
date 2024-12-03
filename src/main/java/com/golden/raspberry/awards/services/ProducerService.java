package com.golden.raspberry.awards.services;

import com.golden.raspberry.awards.dtos.ProducerWinnerResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProducerService {

    private final MovieService movieService;
    private final ProducerMapper producerMapper;
    private final ProducerResponseBuilder producerResponseBuilder;

    public ProducerService(MovieService movieService,
                           ProducerMapper producerMapper,
                           ProducerResponseBuilder producerResponseBuilder) {
        this.movieService = movieService;
        this.producerMapper = producerMapper;
        this.producerResponseBuilder = producerResponseBuilder;
    }

    public ProducerWinnerResponse findMinAndMaxWinners() {
        var producersWinnerList = movieService.findWinners();
        Map<String, List<Integer>> producersWinnerMapYears = producerMapper.mapToProducerYears(producersWinnerList);
        var producersResponse = producerResponseBuilder.buildResponse(producersWinnerMapYears);

        return producerResponseBuilder.buildWinnerResponse(producersResponse);
    }
}
