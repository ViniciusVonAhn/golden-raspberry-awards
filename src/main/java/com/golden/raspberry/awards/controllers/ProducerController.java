package com.golden.raspberry.awards.controllers;

import com.golden.raspberry.awards.dtos.ProducerWinnerResponse;
import com.golden.raspberry.awards.services.ProducerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/producers")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Operation(
            summary = "Retrieve producers with the smallest and largest award intervals",
            description = "This endpoint returns the producers with the smallest and largest time intervals between their wins."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producers successfully retrieved",
                    content = @Content(schema = @Schema(implementation = ProducerWinnerResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    @GetMapping("/min-max-winners")
    public ResponseEntity<ProducerWinnerResponse> getMinAndMaxProducerWinners() {
        var producerWinners = producerService.findMinAndMaxWinners();
        return ResponseEntity.status(HttpStatus.OK).body(producerWinners);
    }
}
