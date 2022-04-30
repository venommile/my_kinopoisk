package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.entity.Review;
import com.example.my_kinopoisk.repository.MovieRepository;
import com.example.my_kinopoisk.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final MovieRepository movieRepository;

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public void editReview(Review review) {
        reviewRepository.save(review);
    }


    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }


}
