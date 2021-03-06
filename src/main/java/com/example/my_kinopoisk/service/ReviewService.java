package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.entity.Review;
import com.example.my_kinopoisk.repository.MovieRepository;
import com.example.my_kinopoisk.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final MovieRepository movieRepository;

    private final UserService userService;

    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    public void editReview(Review review) {
        reviewRepository.save(review);
    }


    public void delete(Long id) {
        reviewRepository.deleteById(id);
    }

    public Optional<Review> find(Long id) {
        return reviewRepository.findById(id);
    }

    public Review get(Long id) {
        return find(id).orElseThrow(() -> new NoSuchElementException("Review with this id does not found"));
    }


}
