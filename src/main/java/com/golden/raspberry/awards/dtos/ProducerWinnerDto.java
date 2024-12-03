package com.golden.raspberry.awards.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerWinnerDto {
    private String name;
    private Integer year;
}
