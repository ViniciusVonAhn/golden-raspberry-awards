package com.golden.raspberry.awards.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "\"year\"")
    private int year;
    private String title;
    private String studios;
    private String producers;
    private boolean winner;
}

