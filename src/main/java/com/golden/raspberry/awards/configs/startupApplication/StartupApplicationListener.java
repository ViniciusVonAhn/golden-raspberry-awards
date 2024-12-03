package com.golden.raspberry.awards.configs.startupApplication;

import com.golden.raspberry.awards.services.MovieService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class StartupApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final MovieService movieService;

    public StartupApplicationListener(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        movieService.importCsv();
    }
}