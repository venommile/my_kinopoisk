package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.MovieViewDto;
import com.example.my_kinopoisk.domain.entity.Movie;
import com.example.my_kinopoisk.service.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BinderService {
    private final MovieService movieService;
    private final GenreService genreService;
    private final MovieMapper movieMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public MovieViewDto bindMovieGenre(Long movieId, Long genreId) {
        var movie = movieService.getMovie(movieId);
        var genre = genreService.getGenre(genreId);
        movie.addGenre(genre);
        return movieMapper.toViewDto(movieService.saveMovie(movie));
    }

}
