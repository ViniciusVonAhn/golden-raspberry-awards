package com.golden.raspberry.awards.services;

import com.golden.raspberry.awards.dtos.ProducerResponseDto;
import com.golden.raspberry.awards.dtos.ProducerWinnerResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ProducerResponseBuilder {

    public List<ProducerResponseDto> buildResponse(Map<String, List<Integer>> producerMapYears) {
        return producerMapYears
                .entrySet()
                .stream()
                .map(entry -> new ProducerResponseDto(
                        entry.getKey(),
                        Collections.min(entry.getValue()),
                        Collections.max(entry.getValue()),
                        Collections.max(entry.getValue()) - Collections.min(entry.getValue())
                ))
                .filter(isMoreThanOneVictory())
                .collect(Collectors.toList());
    }

    private static Predicate<ProducerResponseDto> isMoreThanOneVictory() {
        return producerResponseDto -> producerResponseDto.getInterval() > 0;
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
