package com.golden.raspberry.awards.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerWinnerResponse {
    private List<ProducerResponseDto> min;
    private List<ProducerResponseDto> max;
}
