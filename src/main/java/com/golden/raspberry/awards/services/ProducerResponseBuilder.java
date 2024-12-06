package com.golden.raspberry.awards.services;

import com.golden.raspberry.awards.dtos.ProducerResponseDto;
import com.golden.raspberry.awards.dtos.ProducerWinnerResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProducerResponseBuilder {

    public List<ProducerResponseDto> buildResponse(Map<String, List<Integer>> producerMapYears) {
        return producerMapYears.entrySet().stream()
                .flatMap(entry -> getMinAndMaxIntervals(entry.getKey(), entry.getValue()).stream())
                .collect(Collectors.toList());
    }

    private List<ProducerResponseDto> getMinAndMaxIntervals(String producer, List<Integer> years) {
        if (years.size() < 2) {
            return Collections.emptyList();
        }

        List<Integer> sortedYears = years.stream().sorted().toList();
        ProducerResponseDto minIntervalDto = null;
        ProducerResponseDto maxIntervalDto = null;

        int minInterval = Integer.MAX_VALUE;
        int maxInterval = Integer.MIN_VALUE;

        for (int i = 0; i < sortedYears.size() - 1; i++) {
            for (int j = i + 1; j < sortedYears.size(); j++) {
                int interval = sortedYears.get(j) - sortedYears.get(i);

                if (interval < minInterval) {
                    minInterval = interval;
                    minIntervalDto = createProducerDto(producer, sortedYears.get(i), sortedYears.get(j), interval);
                }

                if (interval > maxInterval) {
                    maxInterval = interval;
                    maxIntervalDto = createProducerDto(producer, sortedYears.get(i), sortedYears.get(j), interval);
                }
            }
        }

        return Stream.of(minIntervalDto, maxIntervalDto)
                .distinct()
                .toList();
    }

    private ProducerResponseDto createProducerDto(String producer, int previousWin, int followingWin, int interval) {
        return new ProducerResponseDto(producer, previousWin, followingWin, interval);
    }

    public ProducerWinnerResponse buildWinnerResponse(List<ProducerResponseDto> producersResponse) {
        var minProduceWinners = getMinProducersWinners(producersResponse);
        var maxProduceWinners = getMaxProducersWinners(producersResponse);

        return new ProducerWinnerResponse(minProduceWinners, maxProduceWinners);
    }

    private List<ProducerResponseDto> getMaxProducersWinners(List<ProducerResponseDto> producersResponse) {
        OptionalInt maxInterval = producersResponse.stream()
                .mapToInt(ProducerResponseDto::getInterval)
                .max();

        return maxInterval.isPresent()
                ? producersResponse.stream()
                .filter(producer -> producer.getInterval() == maxInterval.getAsInt())
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    private List<ProducerResponseDto> getMinProducersWinners(List<ProducerResponseDto> producersResponse) {
        OptionalInt minInterval = producersResponse.stream()
                .mapToInt(ProducerResponseDto::getInterval)
                .min();

        return minInterval.isPresent()
                ? producersResponse.stream()
                .filter(producer -> producer.getInterval() == minInterval.getAsInt())
                .collect(Collectors.toList())
                : Collections.emptyList();
    }
}
