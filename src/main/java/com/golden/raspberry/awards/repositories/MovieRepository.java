package com.golden.raspberry.awards.repositories;

import com.golden.raspberry.awards.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByWinnerTrue();
}
