package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.entity.Review;
import com.example.my_kinopoisk.message.ErrorResponse;
import com.example.my_kinopoisk.service.ReviewService;
import com.example.my_kinopoisk.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NoPermissionException;
import javax.validation.Valid;
import java.security.Principal;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('write')")
    @Validated(OnCreate.class)
    public ResponseEntity<Review> saveReview(@Valid @RequestBody Review review, Principal principal) {
        review.setUsername(principal.getName());
        return ResponseEntity.ok(reviewService.createReview(review));
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Review> updateReview(@Valid @RequestBody Review review, Principal principal) throws NoPermissionException {
        if (principal.getName().equals(review.getUsername())) {
            return ResponseEntity.ok(reviewService.createReview(review));
        }
        throw new NoPermissionException("You didn't have permission to modify it");
    }


}
