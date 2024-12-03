package com.golden.raspberry.awards.services;

import com.golden.raspberry.awards.dtos.MovieDto;
import com.golden.raspberry.awards.dtos.ProducerWinnerDto;
import com.golden.raspberry.awards.exceptions.BusinessException;
import com.golden.raspberry.awards.mappers.MovieMapper;
import com.golden.raspberry.awards.repositories.MovieRepository;
import com.golden.raspberry.awards.useCases.fileManager.CsvReader;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private static final String CSV_FILE_PATH = "src/main/resources/movielist.csv";

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = MovieMapper.INSTANCE;
    }

    public void importCsv() {
        var fileReader = new CsvReader<>(MovieDto.class);
        var movieList = Optional.ofNullable(fileReader.read(CSV_FILE_PATH));

        var movies = movieList.orElseThrow(() -> new BusinessException("ERROR_FILE_IMPORT"))
                .stream()
                .map(movieMapper::toEntity)
                .collect(Collectors.toList());

        movieRepository.saveAll(movies);
    }

    public Set<ProducerWinnerDto> findWinners() {
        var producerWinners = movieRepository.findByWinnerTrue()
                .stream()
                .flatMap(movie -> Arrays.stream(movie.getProducers().split(", | and"))
                        .map(name -> new ProducerWinnerDto(name.stripLeading(), movie.getYear()))
                )
                .toList();
        return new LinkedHashSet<>(producerWinners);
    }
}
