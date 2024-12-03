package com.golden.raspberry.awards.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProducerResponseDto {
    @Schema(description = "Name of the producer", example = "John Doe")
    private String name;
    @Schema(description = "Year of the producer's previous win", example = "2018")
    private Integer previousWin;
    @Schema(description = "Year of the producer's next win", example = "2023")
    private Integer followingWin;
    @Schema(description = "Interval between wins of the producer (in years)", example = "5")
    private Integer interval;
}
