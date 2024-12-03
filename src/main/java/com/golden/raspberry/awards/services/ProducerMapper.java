package com.golden.raspberry.awards.services;

import com.golden.raspberry.awards.dtos.ProducerWinnerDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProducerMapper {

    public Map<String, List<Integer>> mapToProducerYears(Set<ProducerWinnerDto> producersWinner) {
        return producersWinner.stream()
                .collect(Collectors.groupingBy(
                        ProducerWinnerDto::getName,
                        Collectors.mapping(ProducerWinnerDto::getYear, Collectors.toList())
                ));
    }
}
