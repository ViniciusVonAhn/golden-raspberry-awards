package com.golden.raspberry.awards.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {
    private int year;
    private String title;
    private String studios;
    private String producers;
    private String winner;
}
